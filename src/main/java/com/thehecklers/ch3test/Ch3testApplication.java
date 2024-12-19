package com.thehecklers.ch3test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Ch3testApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ch3testApplication.class, args);
    }

}

@RestController
@RequestMapping("/members")
class MembersRestApiController {
    //필드
    List<Member> members = new ArrayList<>();

    // 생성자
    public MembersRestApiController() {
        members.addAll(List.of(
                new Member("user1","user1",18,"010-1234-5678","user1@test.com"),
                new Member("user2","user2",19,"010-1234-5678","user2@test.com"),
                new Member("user3","user3",20,"010-1234-5678","user3@test.com"),
                new Member("user4","user4",22,"010-1234-5678","user4@test.com"),
                new Member("user5","user5",38,"010-1234-5678","user5@test.com"),
                new Member("user6","user6",54,"010-1234-5678","user6@test.com"),
                new Member("user7","user7",43,"010-1234-5678","user7@test.com"),
                new Member("user8","user8",40,"010-1234-5678","user8@test.com"),
                new Member("user9","user9",30,"010-1234-5678","user9@test.com"),
                new Member("user10","user10",33,"010-1234-5678","user10@test.com")
        ));
    }

    // 전체 멤버 출력
    @GetMapping
    Iterable<Member> getMembers() {
        return members;
    }

    // userid로 사용자 정보 출력
    @GetMapping("/{userid}")
    Optional<Member> getMember(@PathVariable("userid") String userid) {
        for (Member member : members) {
            if (member.getUserid().equals(userid)) {
                return Optional.of(member);
            }
        }
        return Optional.empty();
    }

    // Post로 멤버 생성
    @PostMapping("/new")
    Member newMember(@RequestBody Member member) {
        member.setCreateDate(new Date());
        members.add(member);
        return member;
    }

    // PUT으로 userid에 속한 사용자 정보 수정, 만약 사용자가 없으면 새로 생성
    @PutMapping("/{userid}")
    ResponseEntity<Member> updateMember(@PathVariable("userid") String userid, @RequestBody Member member) {
        int memberIndex = -1;
        for (Member mem : members) {
            if (mem.getUserid().equals(userid)) {
                memberIndex = members.indexOf(mem);
                member.setCreateDate(members.get(memberIndex).getCreateDate());
                members.set(memberIndex, member);
            }
        }
        return (memberIndex == -1)
                ? new ResponseEntity<>(newMember(member),HttpStatus.CREATED)
                : new ResponseEntity<>(member,HttpStatus.OK);
    }

    // DELETE로 사용자 삭제
    @DeleteMapping("/{userid}")
    void deleteMember(@PathVariable("userid") String userid) {
        members.removeIf(member -> member.getUserid().equals(userid));
    }
}

// Member 도메인 생성
class Member {
    private String userid;
    private String username;
    private int age;
    private String phone;
    private String email;
    private Date createDate;

    public Member(){}
    public Member(String userid, String username, int age, String phone, String email) {
        this.userid = userid;
        this.username = username;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.createDate = new Date();
    }

    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


}

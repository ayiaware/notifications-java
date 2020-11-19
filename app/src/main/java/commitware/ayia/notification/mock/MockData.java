package commitware.ayia.notification.mock;

import java.util.ArrayList;
import java.util.List;

import commitware.ayia.notification.model.Member;

public class MockData {

   // private int [] time = {10000, 20000, 40000, 50000, 50000, 20000, 60000};

    private int [] time = {10000};

    private List<Member> memberList = new ArrayList<>();

    public MockData() {

        memberList.add(new Member(1,"Johnson", "30 days", 1000, 20000));
        memberList.add(new Member(2,"John", "30 days", 1000, 20000));
        memberList.add(new Member(3,"Vince", "30 days", 1000, 10000));
        memberList.add(new Member(4,"Wilk", "30 days", 1000, 40000));
        memberList.add(new Member(5,"Queen", "30 days", 1000, 70000));
        memberList.add(new Member(6,"King", "30 days", 1000, 80000));

    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }
}

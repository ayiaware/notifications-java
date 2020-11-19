package commitware.ayia.notification.model;

public class Member {


    private int id;
    private String name;
    private long regDate;
    private long expDate;
    private String subscription;

    public Member(int id, String name,String subscription, long regDate, long expDate) {
        this.name = name;
        this.regDate = regDate;
        this.expDate = expDate;
        this.subscription = subscription;
        this.id = id;
    }

    public String getSubscription() {
        return subscription;
    }
    public String getName() {
        return name;
    }
    public long getExpDate() {
        return expDate;
    }
    public int getId() {
        return id;
    }

}

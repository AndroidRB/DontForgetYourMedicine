package rs.fon.dontforgetyourmedicine.data.model;

/**
 * Created by radovan.bogdanic on 3/28/2017.
 */

public class UserModel {

    private int id;
    private String userName;
    private String userEmail;
//    private byte[] userPic;
    private String imgUrl;
    private boolean isVisitor;

    public UserModel() {
    }

    public UserModel(String userName, String userEmail,String imgUrl) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

//    public byte[] getUserPic() {
//        return userPic;
//    }
//
//    public void setUserPic(byte[] userPic) {
//        this.userPic = userPic;
//    }

    public boolean isVisitor() {
        return isVisitor;
    }

    public void setVisitor(boolean visitor) {
        isVisitor = visitor;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

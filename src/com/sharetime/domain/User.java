package com.sharetime.domain;

/**
 * 用户的domain类
 *
 * @Author: yd
 * @Date: 2018/7/21 19:54
 * @Version 1.0
 */
public class User {
    private String age;//年龄
    private String sex;//性别
    private String image;//图片
    private String level;//等级
    private String school;//学校
    private String petName;//昵称
    private String birthday;//生日
    private String province;//省份
    private String password;//密码
    private String studentId;//学号
    private String phoneNum;//手机号
    private String department;//专业
    private String creditScore;//信用积分
    private String personalSignature;//个新签名

    @Override
    public String toString() {

        return phoneNum + "---" + petName + "---" + password + "---" + school
                + "\n---" + age + "---" + sex + "---" + level + "---" + school
                + "\n---" + petName + "---" + password + "---" + department + "---" + studentId
                + "\n---" + phoneNum + "---" + creditScore + "---" + personalSignature;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPersonalSignature() {
        return personalSignature;
    }

    public void setPersonalSignature(String personalSignature) {
        this.personalSignature = personalSignature;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getImage() {
        if(image!=null){
            return image;
        }else {
            return null;
        }
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}

function panduan(){
    var login_name = document.forms["login_form"]["login_name"].value;
    var login_pwd = document.forms["login_form"]["login_pwd"].value;
    if (login_name == null || login_name === "") {
        alert("用户名不能为空。");
        return false;
    }else if(login_pwd == null||login_pwd === ""){
        alert("密码不能为空。");
        return false;
    }
}
/*弹出框样式，覆盖原弹出框*/
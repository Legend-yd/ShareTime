$(document).ready(function () {
    getPersonalInfo();

    /**
     * 获取信息
     * */
    function getPersonalInfo() {
        var phone = getCookie("phoneNum");
        var pwd = getCookie("pwd");
        if (pwd != null && pwd !== "") {
            var data = {"phoneNum": phone, "personalInfo": "get"};
            sendInfo(data);
        }
    }

    /**
     * 发送数据到服务器
     * */
    function sendInfo(data) {
        //提交json数据
        $.ajax({
            type: "POST",
            url: "/PersonalServlet",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (message) {
                dealInfo(message);//处理获取到的个人信息
            },
            error: function (message) {
            }
        });
    }

    /**
     * 退出登录
     * */
    $("#quit").click(function () {
        clearCookie("pwd");
        clearCookie("check");
        location.reload();
    });

    /**
     * 修改信息
     */
    $("#changeInfo").click(function () {
        window.location.href = "changeInfo.html";
    });

    /**
     * 处理获取的个人信息
     *
     * */
    var imageurl;

    function dealInfo(json) {
        var sex = $("#sex");
        var info = $("#info");
        var image = $("#image.txt");
        var level = $("#level");
        var school = $("#school");
        var petName = $("#petName");
        var phoneNum = $("#phoneNum");
        var department = $("#department");
        var creditScore = $("#creditScore");
        var personalSignature = $("#personalSignature");
        if (json.sex === "1") {//性别
            sex.html("♂");
            info.html(json.age + "岁" + "   " + json.birthday);
        } else if (json.sex === "0") {
            sex.html("♀");
            info.html(json.age + "岁" + "    " + json.birthday);
        } else {
            sex.html("");
            info.html("");
        }
        level.html("Lv" + json.level);
        school.html(json.school);
        petName.html(json.petName);
        phoneNum.html(json.phoneNum);
        department.html(json.department);
        creditScore.html(json.creditScore);
        personalSignature.html(json.personalSignature);
        imageurl = json.image;
        if (imageurl != null) {
            $("#image").attr("src", imageurl);
            $("#guide_img").attr("src", imageurl);
        }
        /*var show = dataURLtoBlob(imageurl);
        var m = URL.createObjectURL(show);*/
    }

    /**
     * base 转图片blob对象
     * */

    function dataURLtoBlob(dataurl) {
        var arr = dataurl.split(','),
            mime = arr[0].match(/:(.*?);/)[1],
            bstr = atob(arr[1]),
            n = bstr.length,
            u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new Blob([u8arr], {type: mime});
    }
});
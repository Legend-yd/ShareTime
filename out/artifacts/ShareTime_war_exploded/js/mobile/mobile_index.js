$(document).ready(function () {
    index();

    /**
     * 判断页面的初始状态
     * */
    function index() {
        var phoneNum = getCookie("phoneNum");
        var pwd = getCookie("pwd");
        var title_login = $("div.title_login");//登录模块
        var title_personal = $("div.title_personal");//个人中心模块
        if (pwd != null && pwd !== "") {//判断cookie是否保存了用户密码
            title_login.css("display", "none");
            title_personal.css("display", "block");
        } else {
            title_login.css("display", "block");
            title_personal.css("display", "none");
        }
        getLogo(phoneNum);//获取头像


    }

    /**
     * 跳转页面
     * */
    function putRecommend() {
        window.location.href = "mobileGet.html";
    }

    function getRecommend() {
        window.location.href = "mobilePut.html";
        var h = document.cookie.split(";");
        alert(h[0]);
    }

    /**
     * 发送数据到服务端
     * */
    function sendInfo(data) {
        $.ajax({
            type: "POST",
            url: "/IndexServlet",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (message) {
            },
            error: function () {
            }
        });
    }

    /**
     * 退出登录
     * 将保存的cookie数据清除
     * */
    $("#quit").click(function () {
        clearCookie("pwd");
        clearCookie("check");
        location.reload();
    });

    /**
     * 获取头像
     */
    function getLogo(phoneNum) {
        $.get("/PersonalServlet?phoneNum=" + phoneNum + "&peoplelogo=quest", function (image) {
            if (image !== "noimage") {
                $("#peoplelogo").attr("src", image);
            }
        });
    }

});

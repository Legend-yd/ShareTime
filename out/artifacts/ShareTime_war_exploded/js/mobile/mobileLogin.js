/**
 * 文档就绪事件，防止在文档加载之前就运行jQuery代码
 * */
$(document).ready(function () {
    //进入先判断有没有记住密码和验证码
    checkRemember();

    function checkRemember() {
        $("#name").val(getCookie("phoneNum"));
        if (getCookie("check") === "true") {
            $("input.myCheckBox").attr("checked", true);
            $("#pwd").val(getCookie("pwd"));
        }
        if (getCookie("login") > 1) {
            $(".identifyCode").css("display", "flex");
        }

    }

//判断登陆是否成功

    $("button.btn").click(function () {
        var phoneNum = $("#name").val();
        var pwd = $("#pwd").val();
        var identifyInput = $("#identifyInput").val();
        var input_wrong = $("#input_wrong");
        if (phoneNum == null || phoneNum === "") {
            input_wrong.html("用户名不能为空");//错误警告
            input_wrong.css("display", "block");
        } else if (pwd == null || pwd === "") {
            input_wrong.html("密码不能为空");
            input_wrong.css("display", "block");
        } else if (getCookie("login").length > 1 && (identifyInput == null || identifyInput === "")) {
            input_wrong.html("输入验证码");
            input_wrong.css("display", "block");
        } else {
            input_wrong.css("display", "none");
            var myCheckBox = $("input.myCheckBox");
            if (myCheckBox.is(':checked')) {
                setCookie("phoneNum", phoneNum, 7);
                setCookie("pwd", pwd, 7);
                setCookie("check", "true", 7);//记录复选框选中
            } else {
                clearCookie("check");
            }
            succeed();//将数据传回服务端
        }
    });

    /**
     * 登录之后将数据发送到服务端
     * */
    function succeed() {
        /* $.post("/LoginServlet", function (data, status) {
             alert(data + "状态：\n" + status);
         });*/
        var inputName = $("#name").val();
        var inputPwd = $("#pwd").val();
        var identifyInput = $("#identifyInput").val();//验证码
        var personInfo = {"phoneNum": inputName, "pwd": inputPwd, "identify": identifyInput};
        var loginNum = 0;
        //提交json数据
        $.ajax({
            type: "POST",
            url: "/LoginServlet",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(personInfo),
            dataType: "json",
            success: function (message) {
                var input_wrong = $("#input_wrong");
                if (message.checkNode === "error") {
                    input_wrong.html("验证码错误！");
                    input_wrong.css("display", "block");
                }
                else {
                    input_wrong.css("display", "none");
                }
                if (message.status === "error") {

                    input_wrong.html("用户名或密码错误！");
                    input_wrong.css("display", "block");
                    loginNum = getCookie("login") + 1;
                    setCookie("login", loginNum);
                    if (loginNum.length > 1) {
                        $(".identifyCode").css("display", "flex");
                    } else {
                        $(".identifyCode").css("display", "none");
                    }
                } else if (message.status === "success") {
                    input_wrong.css("display", "none");
                    clearCookie("login");
                    window.location.href = "mobileIndex.html";
                }
            },
            error: function (message) {
                alert("提交失败" + JSON.stringify(message));
            }
        });

    }

    /**
     * 获取验证码
     *
     * 点击看不清再次获取
     * */
    $("#cantSee").click(function () {
        var time = new Date().getTime();
        //后面加一个随机数是为了防止网页从缓存中获取
        document.getElementById("identifyImage").src = "/ImageServlet?d=" + time;
    });
});
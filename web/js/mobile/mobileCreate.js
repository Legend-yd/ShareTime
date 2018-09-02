$(document).ready(function () {
    /**
     * 注册时判断输入框内容
     * */
    $("#createBtn").click(function () {
        var privacyCheck = $("#privacyCheck");//复选框
        var phoneNumber = $("#phoneNumber").val();//手机号
        var password = $("#password").val();//密码
        var checkNumber = $("#checkNumber").val();//验证码
        var warn = $("#warn");
        if (phoneNumber == null || phoneNumber === "") {
            warn.html("手机号不能为空！");
            window.location.href = "#phoneNumber";
            warn.css("display", "block");
        } else if (phoneNumber.length !== 11) {
            window.location.href = "#phoneNumber";

            warn.html("手机号格式错误！");
            warn.css("display", "block");
        } else if (checkNumber == null || checkNumber === "") {
            warn.html("验证码不能为空！");
            window.location.href = "#checkNumber";
            warn.css("display", "block");
        } else if (password == null || password === "") {
            warn.html("密码不能为空！");
            window.location.href = "#password";
            warn.css("display", "block");
        } else if (privacyCheck.is(':checked') === true) {
            var data = {"phoneNum": phoneNumber, "password": password, "checkNum": checkNumber};
            warn.css("display", "none");
            send(data);
        } else {
            warn.html("确认同意条款！");
            window.location.href = "#checkNumber";
            warn.css("display", "block");
        }
    });

    /**
     * 输入框后面的图片变化
     * */
    $("#phoneNumber").keyup(function () {
        var phoneCheck = document.getElementById("phoneCheck");
        var str = $("#phoneNumber").val();
        if (str[0] === "1") {
            phoneCheck.innerHTML = "";
            phoneCheck.style.borderColor = "#b5d4f6";
            phoneCheck.style.backgroundColor = "#b5d4f6";
        } else {
            phoneCheck.innerHTML = "×";
            phoneCheck.style.backgroundColor = "#fc2436";
            phoneCheck.style.color = "#d2f0f7";
        }
        var m = document.getElementById("phoneNumber").value;//手机号
        if (m[0] === "1" && m.length === 11) {
            phoneCheck.innerHTML = "√";
            phoneCheck.style.backgroundColor = "#0fea29";
            phoneCheck.style.color = "#d2f0f7";
        }
    });

    /**
     * 发送验证码
     */
    $("#sendBtn").click(function () {
        var sendBtn = document.getElementById("sendBtn");//获取按钮
        sendBtn.setAttribute("disabled", true);
        sendBtn.style.backgroundColor = "#c0c9dd";
        backTime();//倒计时
    });
    /**
     *倒计时
     */

    var nowTime = 60;

    function backTime() {
        var sendBtn = document.getElementById("sendBtn");//获取按钮
        if (nowTime > 0) {
            sendBtn.innerHTML = nowTime;
            nowTime = nowTime - 1;
            setTimeout(backTime, 1000);
        } else {
            sendBtn.removeAttribute("disabled");
            sendBtn.innerHTML = "重新发送";
            nowTime = 10;
            sendBtn.style.backgroundColor = "#f8eef6";
        }
    }

    /**
     * 美化alert
     * 自己定义一个div并显示出来
     * 获取用户协议
     *
     */
    $("#userAgreement").click(function () {
        var title;
        var textUrl;
        title = "用户协议";
        textUrl = "../txt/mobile/userAgreement.txt";
        var privacy_txt = $("#privacy_txt");
        var privacy_title = $("#privacy_title");
        privacy_title.html(title);
        getInfo(textUrl, privacy_txt);

    });

    /**
     * 隐私权限保护
     * */
    $("#privacyStatement").click(function () {
        var title;
        var textUrl;
        title = "用户协议";
        textUrl = "../txt/mobile/userAgreement.txt";
        var privacy_txt = $("#privacy_txt");
        var privacy_title = $("#privacy_title");
        privacy_title.html(title);
        getInfo(textUrl, privacy_txt);
    });

    /**
     * 获取服务端信息
     * */
    function getInfo(myurl, privacy_txt) {
        $.ajax({
            type: "get",
            url: myurl,
            contentType: "application/text;charset=utf-8",
            dataType: "text",
            success: function (message) {
                privacy_txt.html(message);
                var privacy_div = $("#privacy_div");
                privacy_div.css("transform", "scale(1)");
            },
            error: function (message) {
            }
        });
    }

    /**
     * 向服务端提交数据
     * */
    function send(data) {
        //提交json数据
        $.ajax({
            type: "POST",
            url: "/CreateServlet",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (message) {
                var access = message.access;
                var checkNode = message.checkNode;
                var warn = $("#warn");//警告
                if (access === "yes") {
                    warn.css("display", "none");
                } else if (access === "no") {
                    warn.html("该手机号已被注册！");
                    warn.css("display", "block");
                }
                if (checkNode === "wrong") {
                    warn.html("验证码错误！");
                    warn.css("display", "block");
                } else if (checkNode === "right") {
                    warn.css("display", "none");
                    setCookie("phoneNum", $("#phoneNumber").val(), 7);
                    setCookie("check", "true", 7);
                    var createSucceed = document.getElementById("createSucceed");
                    createSucceed.style.transform = "scale(1)";
                }

            },
            error: function (message) {
            }
        });
    }

    /**
     * 点击按钮隐藏
     * */
    $("#privacy_btn").click(function () {
        var privacy_div = document.getElementById("privacy_div");
        privacy_div.style.transform = "scale(0)";
    });

    /**
     * 留在本页
     * */
    $("#leave").click(function () {
        var createSucceed = document.getElementById("createSucceed");
        createSucceed.style.transform = "scale(0)";
    });
    /**
     * 前往登录
     * */
    $("#goLogin").click(function () {
        window.location.href = "mobileLogin.html";
    });
});

$(document).ready(function () {
    /**
     * 验证用户名
     * */
    $("#sendPhone").click(function () {
        var user_num = $("#user_num").val();//获取账号输入框
        var name_warn = $("#name_warn");//错误警告
        if (user_num == null || user_num === "") {
            name_warn.html("手机号不能为空");
            name_warn.css("display", "block");
        } else if (user_num.length !== 11) {
            name_warn.html("手机号格式错误");
            name_warn.css("display", "block");
        } else {
            var phoneNum = {"phoneNum": user_num};
            sendInfo(phoneNum);
        }
    });

    /**
     * 发送数据给服务端
     * */
    function sendInfo(data) {
        $.ajax({
            type: "POST",
            url: "/ForgetServlet",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (message) {
                var checkDiv = $("#checkDiv"); //验证码区域
                var pwd_div = $("#pwd_div");//密码区域

                var select_first = $("#select_first");
                var select_second = $("#select_second");
                var select_third = $("#select_third");
                var select_forth = $("#select_forth");
                // message.hasOwnProperty("phone");//判断是否含有phone
                if (message.phoneNum === "right") {
                    $("#nameDiv").animate({
                        left: '-100%',
                        opacity: '0'
                    }, 800);
                    checkDiv.animate({
                        left: '0',
                        opacity: '1'
                    }, 800, function () {
                        checkDiv.css("position", "absolute");
                    });
                    yidong('30%', '12em', select_first, select_second);
                } else if (message.phoneNum === "wrong") {
                    var name_warn = $("#name_warn");
                    name_warn.html("用户名错误");
                    name_warn.css("display", "block");
                }
                if (message.checkCode === "right") {
                    checkDiv.animate({
                        left: '-100%',
                        opacity: '0'
                    }, 800);
                    pwd_div.animate({
                        left: '0',
                        opacity: '1'
                    }, 800, function () {
                        pwd_div.css("position", "absolute");
                    });
                    yidong('55%', '9em', select_second, select_third);
                } else if (message.checkCode === "wrong") {
                    var check_warn = $("#check_warn");
                    check_warn.html("验证码错误");
                    check_warn.css("display", "block");
                }
                if (message.pwd === "right") {
                    pwd_div.animate({
                        left: '-100%',
                        opacity: '0'
                    }, 800);
                    $("#succeed").animate({
                        left: '0',
                        opacity: '1'
                    }, 800);
                    yidong('75%', '6em', select_third, select_forth);
                    var phoneNum = $("#user_num").val();//填写的账户名
                    setCookie("phoneNum", phoneNum, 1);//将填写的用户名同步到登录页面上
                    setCookie("check", "true");
                }
            },
            error: function (message) {
            }
        });
    }


    /**
     * 底部横线移动
     * 字体颜色变化
     * @param leftindex:左边的坐标
     * @param widthindex:横线宽度
     * @param hide：不变色的字体
     * @param show:变色的字体
     *
     */
    function yidong(leftindex, widthindex, hide, show) {
        var bottomLine = $("#bottomLine");//底部横线
        bottomLine.animate({
            left: leftindex,
            width: widthindex
        }, 800);
        hide.css("color", "#9d9d9d");
        show.css("color", "#63c5fb");
    }

    /**
     * 发送验证码
     */
    $("#sendBtn").click(function () {
        var sendBtn = document.getElementById("sendBtn");//获取按钮
        sendBtn.setAttribute("disabled", "true");
        sendBtn.style.backgroundColor = "#c0c9dd";
        backTime();//倒计时
    });

    /**
     *倒计时
     *
     * 1、在jquery中调用setTimeout时需要写在
     * ready外面让他成为全局函数，否则会报错
     *
     * 2、或者直接写函数名，不用括号也不用引号
     */
    var nowTime = 30;

    function backTime() {
        var sendBtn = document.getElementById("sendBtn");//获取按钮
        if (nowTime > 0) {
            sendBtn.innerHTML = nowTime.toString();
            nowTime = nowTime - 1;
            setTimeout(backTime, 1000);
        } else {
            sendBtn.removeAttribute("disabled");
            sendBtn.innerHTML = "重新发送";
            nowTime = 60;
            sendBtn.style.backgroundColor = "#f8eef6";
        }
    }

    /**
     * 提交验证码
     * */
    $("#checkCodeBtn").click(function () {
        var checkNumber = $("#checkNumber").val();//验证码框
        if (checkNumber != null && checkNumber !== "") {
            var checkCode = {"checkCode": checkNumber};
            sendInfo(checkCode);
        } else {
            var check_warn = $("#check_warn");
            check_warn.css("display", "block");
        }
    });

    /**
     * 判断密码的等级
     * */
    $("#pwd").keyup(function () {
        var pwd_level = document.getElementById("pwd_level");
        var pwd = document.getElementById("pwd").value;
        var level1 = document.getElementById("level1");
        var level2 = document.getElementById("level2");
        var level3 = document.getElementById("level3");
        var level_text = document.getElementById("level_text");
        if (pwd.length > 0) {
            pwd_level.style.display = "flex";
        } else {
            pwd_level.style.display = "none";
        }
        if (pwd.length > 2 && pwd.length < 6) {
            level1.style.backgroundColor = "#faa809";
            level2.style.backgroundColor = "#fa2b15";
            level3.style.backgroundColor = "#fa2b15";
            level_text.innerHTML = "弱";
            level_text.style.color = "#faa809";
        } else if (pwd.length > 6 && pwd.length < 12) {
            level1.style.backgroundColor = "#faa809";
            level2.style.backgroundColor = "#faa809";
            level3.style.backgroundColor = "#fa2b15";
            level_text.innerHTML = "中";
            level_text.style.color = "#faa809";
        } else if (pwd.length > 12) {
            level1.style.backgroundColor = "#22fa24";
            level2.style.backgroundColor = "#22fa24";
            level3.style.backgroundColor = "#22fa24";
            level_text.innerHTML = "强";
            level_text.style.color = "#22fa24";

        }
    });

    /***
     * 判断两次密码是否一致
     * 重复密码框的输入监听
     */
    /*$("#re_pwd").keyup(function () {

    });*/

    $("input.pwd").keyup(function () {
        var pwd = document.getElementById("pwd").value;//第一次
        var re_pwd = document.getElementById("re_pwd").value;//二次密码
        var pwd_submit = document.getElementById("pwd_submit");//提交按钮
        var pwd_warn = document.getElementById("pwd_warn");//两次密码不一致警告
        if (pwd !== null && pwd !== "" && re_pwd !== null && re_pwd !== "") {
            if (pwd === re_pwd) {
                pwd_warn.style.display = "none";
                pwd_submit.removeAttribute("disabled");//按钮恢复使用
                pwd_submit.style.backgroundColor = "#1bc4f3";
            } else {
                pwd_warn.style.display = "block";
                pwd_submit.setAttribute("disabled", "true");//按钮不可用
                pwd_submit.style.backgroundColor = "#d0d8d7";
            }
        }
    });

    /**
     * 提交密码
     * */

    $("#pwd_submit").click(function () {
        var pwdData = {"pwd": $("#pwd").val()};
        sendInfo(pwdData);
    });
});
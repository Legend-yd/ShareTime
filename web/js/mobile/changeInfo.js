$(document).ready(function () {


    initialize();
    var phoneNum;
    var studentId;

    /**
     * 初始化
     * */
    function initialize() {
        var phoneNumDiv = $("#phoneNum");
        var password = $("#password");
        phoneNum = getCookie("phoneNum");
        //将手机号隐藏
        phoneNumDiv.val(hideNum(phoneNum, 4, 6));

        password.val(getCookie("pwd"));
        phoneNumDiv.attr("disabled", "disabled");
        var data = {"phoneNum": getCookie("phoneNum"), "personalInfo": "get"};
        getSchool();
        GetInfo(data);
    }

    /**
     * 上传头像
     * */
    var imageString;
    $("#inputImage").change(function () {
        var objUrl = $(this)[0].files[0];
        var reader = new FileReader();//新建一个FileReader
        reader.readAsDataURL(objUrl);//读取文件,保存为base64 格式
        reader.onload = function (evt) { //读取完文件之后会回来这里
            imageString = evt.target.result;
            //获得一个http格式的url路径:mozilla(firefox)||webkit or chrome
            var windowURL = window.URL || window.webkitURL;
            //createObjectURL创建一个指向该参数对象(图片)的URL
            var dataURL = windowURL.createObjectURL(objUrl);
            $("#showImage").attr("src", dataURL);
        }
    });

    /**
     * 点击省份的下拉框
     * */
    var school = $("#school");
    var province = $("#province");

    province.change(function () {
        var id = $(this).get(0).selectedIndex;
        setSchool(id + 1);
    });


    /**
     *设置省份
     */
    function setProvince(message) {
        var json = JSON.parse(message);
        var myhtml = "";
        var name;
        for (var i = 0; i < json.length; i++) {
            name = json[i].name;
            myhtml += "<option>" + name + "</option>";
        }
        var province = $("#province")
        province.empty();
        province.append(myhtml);
    }

    /**
     * 设置学校
     * */
    function setSchool(id) {
        var message = line[id];
        var json = JSON.parse(message);
        var myhtml = null;
        var name;
        for (var i = 0; i < json.length; i++) {
            name = json[i].name;
            myhtml += "<option value='" + name + "'>" + name + "</option>";
        }
        school.html(myhtml);
    }

    /**
     * 保存
     * */
    $("#save").click(function () {

        var password = getCookie("pwd");
        var sex = $("#sex").val();
        var school = $("#school").val();
        var petName = $("#petName").val();
        var birthday = $("#birthday").val();
        var province = $("#province").val();
        var department = $("#department").val();
        var studentIdDiv = $("#studentId").val();
        if (studentId === "" || studentId == null) {
            studentId = studentIdDiv;
        }
        if (petName === "" || petName == null) {
            window.location.href = "#petName";
        } else if (birthday === "" || birthday == null) {
            window.location.href = "#birthday";
        } else if (sex === "" || sex == null) {
            window.location.href = "#sex";
        } else if (province === "" || province == null) {
            window.location.href = "#province";
        } else if (school === "" || school == null) {
            window.location.href = "#school";
        } else if (department === "" || department == null) {
            window.location.href = "#department";
        } else if (studentIdDiv === "" || studentIdDiv == null) {
            window.location.href = "#studentId";
        } else {
            var data = {
                "image": imageString,
                "phoneNum": phoneNum,
                "petName": petName,
                "birthday": birthday,
                "password": password,
                "sex": sex,
                "province": province,
                "school": school,
                "department": department,
                "studentId": studentId
            };
            sendInfo(data);
            $("#loadDiv").css("display", "block");
        }
    });

    /**
     * 返回
     * */
    $("#back").click(function () {
        window.location.href = "mobilePersonal.html";
    });

    /**
     * 获取省份，学校信息
     * */
    var line;//读取的每一行的信息
    function getSchool() {
        $.ajax({
            type: "get",
            url: "../txt/mobile/myschool.txt",
            contentType: "application/text;charset=utf-8",
            dataType: "text",
            success: function (message) {
                line = message.split("\n");
                setProvince(line[0])
            },
            error: function (message) {
            }
        });
    }

    /**
     * 发送数据到服务端
     * */
    function sendInfo(data) {
        $.ajax({
            type: "POST",
            url: "/SavePersonalInfoServlet",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (message) {
                if (message.update === "success") {
                    $("#loadDiv").css("display", "none");
                    var successAlert = $("#successAlert");
                    successAlert.css("display", "block");
                    successAlert.animate({
                        opacity: '1',
                        left: '2em'
                    }, 500, function () {
                        var num = 3;
                        var time = $("#time");
                        var start = setInterval(myTime, 1000);

                        function myTime() {
                            time.html(num);
                            num--;
                            if (num === -1) {
                                clearInterval(start);
                                window.location.href = "mobilePersonal.html"
                            }
                        }

                        var maskSuccess = $("#maskSuccess");
                        maskSuccess.slideUp(1000);
                    });
                }
            },
            error: function () {
                var errorAlert = $("#errorAlert");
                errorAlert.css("display", "block");
                errorAlert.animate({
                    opacity: '1',
                    left: '2em'
                }, 500, function () {
                    var maskError = $("#maskError");
                    maskError.slideUp(1000);
                    setTimeout(function () {
                        errorAlert.animate({
                            opacity: '0',
                            left: '0'
                        }, 500, function () {
                            errorAlert.css("display", "none");
                        });
                    }, 2000);
                });

            }
        });
    }

    /**
     * 获取个人信息
     * */
    function GetInfo(data) {
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
     * 获取到的个人信息显示出来
     * */
    function dealInfo(json) {
        studentId = json.studentId;
        var sex = $("#sex");
        var school = $("#school");
        var petName = $("#petName");
        var birthday = $("#birthday");
        var province = $("#province");
        var showImage = $("#showImage");
        var department = $("#department");
        var studentIdDiv = $("#studentId");
        sex.val(json.sex);
        school.val(json.school);
        petName.val(json.petName);
        birthday.val(json.birthday);
        province.val(json.province);
        studentIdDiv.val(hideNum(studentId, 4, 5));
        department.val(json.department);
        if (json.image != null) {
            showImage.attr("src", json.image);
        }
        imageString = json.image;
    }

    /**
     * 隐藏数字，传入值为字符串，隐藏信息的首地址索引，要隐藏的位数
     * num,index,length
     *
     */
    function hideNum(num, mindex, mlength) {
        if (num != null) {
            var phone = num.split('');
            if (num != null) {
                for (var i = 0; i < mlength; i++) {
                    phone[mindex - 1 + i] = "*";
                }
                num = phone.join('');
                return num;
            }
        }
    }
});
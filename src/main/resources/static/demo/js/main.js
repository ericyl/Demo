$(function () {
    var subPages = location.pathname.substring(1).split("/");
    var subPage = subPages[0];
    if (subPage === "")
        subPage = "main";
    else subPage = subPage.split(".")[0];
    jQuery('#' + subPage).addClass("active");


    $.each(subPages, function (i, item) {
        var info = item;
        if (item.endWith(".html")) {
            item = item.replace(/.html/, "");
            info = item
        }

        if (item === "invoice-info")
            info = "票据信息";
        else if (item === "write-off")
            info = "票据核销";
        else if (item === "add")
            info = "新增";
        else if (item === "edit")
            info = "编辑";
        else if (item === "disable")
            info = "作废";
        else if (item === "input-outpatient")
            info = "门诊开票";
        else if (item === "input-hospitalization")
            info = "住院开票";
        else if(item === "write-off-paper")
            info = "纸质票据核销";
        else {
            var name = jQuery("#name").val();
            if (name != null)
                info = name;
        }

        var urls = subPages.slice(0, i + 1);
        var reducer = function add(url, item) {
            return url + "/" + item;
        };

        var url = urls.reduce(reducer, "");

        if (i !== subPages.length - 1)
            jQuery('.breadcrumb').append('<li class="breadcrumb-item"><a href="' + url + '.html">' + info + '</a></li>');
        else
            jQuery('.breadcrumb').append('<li class="breadcrumb-item active" aria-current="page">' + info + '</li>');
    });

    $(".nav-item.active").parent().parent().addClass('show');
    // $(".nav-item .collapse").addClass('show');

});

String.prototype.endWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substring(this.length - str.length) == str)
        return true;
    else
        return false;
    return true;
};

var citys = {
    11: "北京",
    12: "天津",
    13: "河北",
    14: "山西",
    15: "内蒙古",
    21: "辽宁",
    22: "吉林",
    23: "黑龙江",
    31: "上海",
    32: "江苏",
    33: "浙江",
    34: "安徽",
    35: "福建",
    36: "江西",
    37: "山东",
    41: "河南",
    42: "湖北",
    43: "湖南",
    44: "广东",
    45: "广西",
    46: "海南",
    50: "重庆",
    51: "四川",
    52: "贵州",
    53: "云南",
    54: "西藏",
    61: "陕西",
    62: "甘肃",
    63: "青海",
    64: "宁夏",
    65: "新疆",
    71: "台湾",
    81: "香港",
    82: "澳门",
    91: "国外"
};

function decideIdentification(value) {
    var num = 0;
    if (!/^\d{17}(\d|x)$/i.test(value)) {
        return false;
    }
    value = value.replace(/x$/i, "a");
    if (citys[parseInt(value.substr(0, 2))] == null) {
        return false;
    }
    var birthday = value.substr(6, 4) + "-" + Number(value.substr(10, 2)) + "-" + Number(value.substr(12, 2));
    var d = new Date(birthday.replace(/-/g, "/"));
    if (birthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) {
        return false;
    }
    for (var i = 17; i >= 0; i--)
        num += (Math.pow(2, i) % 11) * parseInt(value.charAt(17 - i), 11);
    if (num % 11 != 1) {
        return false;
    }

    return true;
}



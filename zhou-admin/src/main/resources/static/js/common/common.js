/**
 * Created by bone on 2017/8/2.
 */

//以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
if($.validator){
    $.validator.setDefaults({
        highlight: function (element) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function (element) {
            element.closest('.form-group').removeClass('has-error').addClass('has-success');
        },
        errorElement: "span",
        errorPlacement: function (error, element) {
            if (element.is(":radio") || element.is(":checkbox")) {
                error.appendTo(element.parent().parent().parent());
            } else {
                error.appendTo(element.parent());
            }
        },
        errorClass: "help-block m-b-none",
        validClass: "help-block m-b-none"
    });
}

getDate  = function (obj) {
    var oYear = obj.getFullYear(),
        oMonth = obj.getMonth() + 1,
        oDay = obj.getDate();
    var oDate =
        oYear +
        "-" +
        this.getZeroFormat(oMonth) +
        "-" +
        this.getZeroFormat(oDay);
    return oDate;
},
getZeroFormat  = function(num) {
    if (parseInt(num) < 10) {
        num = "0" + parseInt(num);
    }
    return num;
};
emailverify = function(rule, value, callback){
    setTimeout(function(){
        if (!/(^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$)/i.test(value)) {
            callback(new Error('请书写正确格式'));
        } else {
            callback();
        }
    }, 500);
};


var commonUtils = {};

commonUtils.getUrlParam = function (name) {
    if(window.location) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) {
            return decodeURIComponent(r[2]);
        }
    }
    return null;
};

commonUtils.selectMenu = function (settings) {

    var options = {
        title : '选择父节点',
        currentId : null,
        yesCallback : function (node) {

        }
    };

    options = $.extend({}, options, settings);

    layer.open({
        id: 'LAY_selectMenu', //设定一个id，防止重复弹出
        type: 2,
        title: options.title,
        shadeClose: true,
        shade: 0.8,
        area: ['380px', '60%'],
        moveType: 1,//拖拽模式，0或者1
        content: '/common/selectMenu',
        btn: ['确认', '取消'],
        yes:function(index,layero){
            var selectMenuWindow = $("#LAY_selectMenu").find("iframe")[0].contentWindow;
            var selectNode = selectMenuWindow.selectMenu_selectNode;

            var ztreeObj = selectMenuWindow.selectMenu_ztreeObj;

            if(options.currentId){
                var currentNode = ztreeObj.getNodeByParam("id", options.currentId);
                if(isChild(currentNode , selectNode)){
                    layer.alert("不能选择当前节点或者当前节点的子节点");
                    return false;
                }
            }

            options.yesCallback(selectNode);
            layer.close(index);
        }
    });

    function isChild(currentNode, childNode) {

        if(currentNode.id == childNode.id){
            return true;
        }

        if(currentNode.isParent) {
            var childrens = currentNode.children;
            for(var i=0; i<childrens.length; i++){
                return isChild(childrens[i], childNode);
            }
        }

        return false;
    }

};
dataErrorHandler = function(res){
    if(res.code == 401 ){
        app.$alert(res.message, '提示', {
            confirmButtonText: '确定',
            type: 'warning',
            callback: action => {
                window.parent.location.href="/logout";
            }
        });
    }else if(res.code == 400){
        app.$message({
            showClose: true,
            message:res.message,
            type: 'error'
        });
    }else if(res.code == 403){
        app.$message({
            showClose: true,
            message:"没有操作权限",
            type: 'warning'
        });
    }else if(res.code == 500){
        app.$message({
            showClose: true,
            message:"系统异常!",
            type: 'error'
        });
    }
};

formatterAmount=function(s, n) {
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
};

formatterCard=function(id){
    var v, j, sj, rv = "";
    id = $.trim(id);
    id = id.replace(/\s+/g, "");
    id = id.replace(/-/g, "");
    v = id.replace(/,/g, "").split(".");
    j = v[0].length % 4;
    sj = v[0].substr(j).toString();
    for (var i = 0; i < sj.length; i++) {
        rv = (i % 4 == 0) ? rv + "-" + sj.substr(i, 1) : rv + sj.substr(i, 1);
    }
    var rvalue = (v[1] == undefined) ? v[0].substr(0, j) + rv : v[0].substr(0, j) + rv + "." + v[1];
    if (rvalue.charCodeAt(0) == 44) {
        rvalue = rvalue.substr(1);
    }
    if (rvalue[0] === "-") {
        rvalue = rvalue.substring(1);
    }
    return rvalue;
};

isMaxfile = function(file,fileList){
    app.loading = true;
};




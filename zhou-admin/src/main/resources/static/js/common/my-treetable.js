var TreeTable = function(ele, opt) {
        this.$element = ele,

        this.defaults = {

        },
        this.options = $.extend({}, this.defaults, opt)
}
TreeTable.prototype = {
    //iCheck
    changeICheck:function(){
        this.$element.find("input[name='selected']").iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green'
        });
    },
    //增加表头
    addTableHead:function(data){
        var tr=$("<tr>",{

        });
        for(var i = 0,j = data.length ; i < j ; i++ ){
            var td = $("<td></td>");
            td.text(data[i]);
            tr.append(td);
        }
    },
    //
    //设置单行数据
    setRowHtml:function(data,dataField,type,index){
        var tr = $("<tr>",{
            id:"row"+index,
            "data-tt-id":data.treegrid,
            "data-tt-parent-id":data.parentid?data.parentid:0
        });
        this.$element.append(tr);
        var html ="";
        for(var i = 0,j = dataField.length ; i < j ; i++ ){
            if(i === 0){
                html+="<td><input name='selected' value='"+ data.id +"' type='"+ type+"'>"+data[dataField[i]]+"</td>";
            }else{
                html+="<td>"+data[dataField[i]]+"</td>";
            }
        }
        this.$element.find('#row'+index).html(html);
    },
    //改变数据
    changeData:function(data){
        var dataF = [];
        var dataS = [];
        var dataFa = [];
        var dataSa = [];
        var index = 1;
        for(var i = 0,j= data.length;i < j;i++){
            if(data[i].parentId === '0'){
                data[i].treegrid = index;
                index++;
                dataF.push(data[i]);
            }
        }
        for(var a = 0,b =  dataF.length;a < b;a++){
            var c=1;
            for(var  n = 0,m = data.length;n<m; n++){
                if(data[n].parentId === dataF[a].id){
                    data[n].treegrid =dataF[a].treegrid+"-"+c;
                    data[n].parentid = dataF[a].treegrid;
                    c++;
                    dataS.push(data[n]);
                }
            }
        }
        for(var aa = 0,ba = dataS.length;aa < ba;aa++){
            var d=1;
            for(var  na = 0,ma = data.length;na < ma; na++){
                if(data[na].parentId === dataS[aa].id){
                    data[na].treegrid =""+ dataS[aa].treegrid+"-"+d;
                    data[na].parentid = dataS[aa].treegrid;
                    d++;
                }
            }
        }
        return data
    },
    //设置所有行数据
    setRowAllHtml:function(data,dataField,type){
        if(data){
            for(var i= 0,j = data.length; i < j;i++){
                this.setRowHtml(data[i],dataField,type,i)
            }
        }
    },
    //
    getdata:function(){
        var $this = this;
        $.ajax({
            url:this.options.url,
            async:true,
            dataType:"json",
            type:"post",
            success:function(data){
                if(data){
                    var dataList=$this.changeData(data.list);
                    $this.data = dataList;
                    $this.$element.html("");
                    $this.setRowAllHtml(dataList,$this.options.dataField,$this.options.type);
                    $this.$element.treetable({
                        expandable : true,
                        initialState: "expanded",
                    });
                    $this.addTableHead($this.options.dataHead);
                    $this.changeICheck();
                }
            },
            error:function(){
                layer.alert("系统错误，请稍后在请求。");
            }
        });
    },
    //初始化
    init:function () {
        return this.getdata();
    }
    //重新加
};

$.fn.myTreeTable =function (options) {
    var treeTable = new TreeTable(this, options);
    //调用其方法
    return treeTable.init();
}
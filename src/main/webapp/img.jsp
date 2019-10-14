<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>

<script>
    $(function () {
        $("#books").jqGrid({
            styleUI: "Bootstrap",
            url: "${app}/banner/findAll",
            datatype: "json",
            colNames: ["编号", "标题", "冻结状态", "创建时间", "描述", "图片"],
            autowidth: true,
            pager: "#page",
            page: 1,
            rowNum: 5,
            height: "60%",
            viewrecords: true,
            rowList: [5, 7, 8],
            editurl: "${app}/banner/edit",
            cellEdit: false,
            toolbar: [true, "top"],
            multiselect: true,
            colModel: [
                {
                    name: "id", align: "center"
                },
                {
                    name: "name", editable: true, align: "center"
                },
                {
                    name: "context", editable: true, align: "center", edittype: "select", editoptions: {
                        value: "否:否;是:是"
                    }
                },
                {
                    name: "create_date", align: "center"
                },
                {
                    name: "state",
                    editable: true, align: "center"
                },
                {
                    name: "url",
                    editable: true, edittype: "file", align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img style='height: 80px;width: 100px' src='${app}/img/" + cellvalue + "' />"
                    }
                }
            ],
        }).jqGrid("navGrid", "#page",
            {
                search: false
            },
            {
                closeAfterEdit: true,
                beforeShowForm: function (data) {
                    data.find("#url").attr("disabled", true)
                }
            },
            {
                closeAfterAdd: true,
                afterSubmit: function (data) {
                    var text = data.responseText;
                    $.ajaxFileUpload({
                        url: "${app}/banner/upload",
                        fileElementId: "url",
                        data: {bannerId: text},
                        success: function (data) {
                            $("#books").trigger("reloadGrid")
                        }
                    })
                    return data
                }
            },
            {}
        )
    })

    function showModal() {
        $.ajax({
            url: "${app}/banner/download",
            datatype: "json",
            type: "post",
        })
    }


</script>
<div class="panel panel-default">
    <div class="panel-heading"><h1>轮播图管理</h1></div>

</div>
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#"><strong>轮播图信息</strong></a></li>
    <li role="presentation"><a href="#profile" onclick="showModal()" aria-controls="profile" role="tab"
                               data-toggle="tab">导出</a></li>
</ul>
<table id="books"></table>
<div id="page"></div>
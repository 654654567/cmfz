<%@page isELIgnored="false" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>

<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 1000px;height:400px;"></div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '近七天用户注册曲线图'
        },
        tooltip: {},
        legend: {
            data: ['人数']
        },
        xAxis: {
            data: ["六天前", "五天前", "四天前", "三天前", "两天前", "一天前", "今天"]
        },
        yAxis: {},
        series: [{
            name: '人数',
            type: 'line',
            data: [5, 20, 36, 10, 10, 20]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    $.ajax({
        url: "${app}/user/queryDays",
        datatype: "JSON",
        type: "POST",
        success: function (data) {
            console.log(data);
            myChart.setOption({
                xAxis: {data: data.xxx},
                series: [{
                    data: data.yyy
                }]
            })
        }
    })
</script>
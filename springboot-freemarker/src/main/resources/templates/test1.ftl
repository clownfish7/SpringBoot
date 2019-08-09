<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <title>Hello World!</title>
</head>
<body>
Hello ${name}!
<hr/>


list:
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
<#list stus as stu>
<tr>
<#--_index：得到循环的下标，使用方法是在stu后边加"_index"，它的值是从0开始-->
<#--1、注释，即<#‐‐和‐‐>，介于其之间的内容会被freemarker忽略-->
<#--2、插值（Interpolation）：即${..}部分,freemarker会用真实的值代替${..}-->
<#--3、FTL指令：和HTML标记类似，名字前加#予以区分，Freemarker会解析标签中的表达式或逻辑。-->
<#--4、文本，仅文本信息，这些不是freemarker的注释、插值、FTL指令的内容会被freemarker忽略解析，直接输出内容。-->
    <td>${stu_index + 1}</td>
    <td>${stu.name}</td>
    <td>${stu.age}</td>
    <td>${stu.money}</td>
</tr>
</#list>
</table>
<hr/>


map:<br/>
输出stu1的学生信息：<br/>
姓名：${stuMap['stu1'].name}<br/>
年龄：${stuMap['stu1'].age}<br/>
输出stu1的学生信息：<br/>
姓名：${stuMap.stu1.name}<br/>
年龄：${stuMap.stu1.age}<br/>
遍历输出两个学生信息：<br/>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
<#list stuMap?keys as k>
<tr>
    <td>${k_index + 1}</td>
    <td>${stuMap[k].name}</td>
    <td>${stuMap[k].age}</td>
    <td>${stuMap[k].money}</td>
</tr>
</#list>
</table>
<hr/>


if:<br/>
<table>
    <tr>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
<#list stus as stu>
<tr>
    <td <#if stu.name =='小明'>style="background:red;"</#if>>${stu.name}</td>
    <td>${stu.age}</td>
    <td>${stu.money}</td>
</tr>
</#list>
</table>
<br/>


<br/>
<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
<#assign data=text?eval />
开户行：${data.bank} 账号：${data.account}

</body>
</html>

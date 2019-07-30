
https://github.com/malsup/form

这是一个全面支持表单的jQuery插件，支持文件上传，包含以下一些方法：

-ajaxForm
-ajaxSubmit
- formToArray
- formSerialize
- fieldSerialize
- fieldValue
- clearForm
- clearFields
- resetForm

示例代码：

?
1
2
3
4
5
6
// wait for the DOM to be loaded  $(document).ready(function() {
     // bind 'myForm' and provide a simple callback function
     $('#myForm').ajaxForm(function() {
         alert("Thank you for your comment!");
     });
});
/**
 * @preserve https://gitee.com/chenjerome/bootstrap-table-merge-cells
 *
 * Version 1.0
 *
 * Copyright (c) 2018-2018 chenjj
 *
 * Licensed under the MIT License
 **/

(function ($) {
  $.fn.mergeCells = function (fields) {
    var options = $(this).bootstrapTable('getOptions');
    if (options.cardView) {
      return false;
    }
    var data = $(this).bootstrapTable('getData', true);
    if (fields.length == 0 || data.length == 0) {
      return false;
    } 
    for(var i = 0, len = fields.length; i < len; i++){
        merge(fields, this);
        fields.pop();
    }
    
    function merge(fields,target) {
        //声明一个map计算相同属性值在data对象出现的次数和
        var sortMap = {};
        for(var i = 0; i < data.length; i++){
          var key = "";
          for (var f = 0; f < fields.length; f++) {
              key = key + "#" + data[i][fields[f]];
          }
          if(sortMap.hasOwnProperty(key)){
              sortMap[key] = sortMap[key] * 1 + 1;
          } else {
              sortMap[key] = 1;
          }
        }
        var index = 0;
        var fieldName = fields[fields.length - 1];
        for(var prop in sortMap){
            var count = sortMap[prop] * 1;
            $(target).bootstrapTable('mergeCells',{index:index, field:fieldName, colspan: 1, rowspan: count});   
            index += count;
        }
    }
  }
})(jQuery);
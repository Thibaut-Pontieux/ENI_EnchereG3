var $table = $('#table')
var $remove = $('#remove')
var selections = []


function getIdSelections() {
	return $.map($table.bootstrapTable('getSelections'), function (row) {
    				return row.id
    })
}

function responseHandler(res) {
	$.each(res.rows, function (i, row) {
      		row.state = $.inArray(row.id, selections) !== -1
    })
    return res
}

function detailFormatter(index, row) {
	console.log(row)
	var html = []
    html.push('<p><b>Vendeur : </b> ' + row.author + '</p>');
    html.push('<p><b>Date : </b> ' + row.date + '</p>');
    html.push('<p><b>Article : </b> ' + row.name + '</p>');
    html.push('<p><b>Prix : </b> ' + row.price + '</p>');
    return html.join('')
}

function operateFormatter(value, row, index) {
	return [
    	'<a class="like" href="javascript:void(0)" title="Like">',
    	'<i class="fa fa-heart"></i>',
    	'</a>  ',
    	'<a class="remove" href="javascript:void(0)" title="Remove">',
    	'<i class="fa fa-trash"></i>',
    	'</a>'
    ].join('')
}

window.operateEvents = {
	'click .like': function (e, value, row, index) {
	alert('You click like action, row: ' + JSON.stringify(row))
	},
    
	'click .remove': function (e, value, row, index) {
    	$table.bootstrapTable('remove', {
        	field: 'id',
        	values: [row.id]
      	})
    }
 }

function totalTextFormatter(data) {
	return 'Total'
}

function totalNameFormatter(data) {
	return data.length
}

function totalPriceFormatter(data) {
	var field = this.field
	return '$' + data.map(function (row) {
    	return +row[field].substring(1)
    }).reduce(function (sum, i) {
    	return sum + i
	}, 0)
}

function initTable() {
	$table.bootstrapTable('destroy').bootstrapTable({
    	height: 550,
    	locale: $('#locale').val(),
    	columns: [
    		[{
        		field: 'state',
        		rowspan: 2,
        		align: 'center',
        		valign: 'middle',
				visible : false
        	},
			 {
        	 	field: 'name',
         	 	title: 'Article',
        		rowspan: 2,
        		align: 'center',
        		valign: 'middle',
        		sortable: true,
        		footerFormatter: totalTextFormatter
        	 },
			  {
        	  	title: 'informations',
        	  	colspan: 4,
        	  	align: 'center'
        	  }],
        	[
		 	{
        		field: 'price',
        	  	title: 'prix',
          		sortable: true,
         		align: 'center',
          		footerFormatter: totalPriceFormatter
         	},
			{
        		field: 'date',
        	  	title: 'date',
          		sortable: true,
         		align: 'center',
          		footerFormatter: totalPriceFormatter
         	},
		 	{
        		field: 'author',
          		title: 'Vendeur',
          		align: 'center',
          		clickToSelect: false
         	},
			{
				field: 'operate',
          		title: 'Actions',
          		align: 'center',
          		clickToSelect: false,
          		events: window.operateEvents,
          		formatter: operateFormatter
			}]
      	],
    })
    $table.on('check.bs.table uncheck.bs.table ' +
      			'check-all.bs.table uncheck-all.bs.table',
    	function () {
      		$remove.prop('disabled', !$table.bootstrapTable('getSelections').length)

      		// save your data, here just save the current page
      		selections = getIdSelections()
      		// push or splice the selections if you want to save all data selections
    })
    $remove.click(function () {
      var ids = getIdSelections()
      $table.bootstrapTable('remove', {
        field: 'id',
        values: ids
      })
      $remove.prop('disabled', true)
    })
  }

$(function() {
initTable()

    $('#locale').change(initTable)
  })


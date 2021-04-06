const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    // "defaultContent":""
                },
                {
                    "data": "description",
                    // "defaultContent":""
                },
                {
                    "data": "calories",
                    // "defaultContent":""
                },

                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});

function filter() {
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: $('#filter').serialize(),
        success: function (data) {
            ctx.datatableApi.clear().rows.add(data).draw();
        }
    });
}

function filterOff() {
    $("#filter").find(":input").val("");
    filter();
}
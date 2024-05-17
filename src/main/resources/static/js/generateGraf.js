

document.addEventListener("DOMContentLoaded", function () {
    fetch("/admin/getListMarkForAgency", {
        method: 'GET'
    }) // Замените URL на свой
        .then(function (response) {
            if (response.ok) {
                return response.json();
            }
            throw new Error("Ошибка запроса на сервер");
        })
        .then(function (data) {
            var xValues = [0, 1, 2, 4, 5, 6, 7, 8, 9, 10];
            new Chart("myChart", {
                type: "line",
                data: {
                    labels: xValues,
                    datasets: [{
                        data: x.markQuality,
                        borderColor: "red",
                        fill: false
                    }, {
                        data: x.prinProdQuality,
                        borderColor: "green",
                        fill: false
                    }, {
                        data: x.otpProdQuality,
                        borderColor: "blue",
                        fill: false
                    }, {
                        data: x.qualityProduct,
                        borderColor: "yellow",
                        fill: false
                    }, {
                        data: x.qualityProduct,
                        borderColor: "pink",
                        fill: false
                    }]
                },
                options: {
                    legend: {display: false}
                }
            });
        })
        .catch(function (error) {
            // Обработка ошибок
            console.log(error);
        });
    fetch('/admin/getCustomAgency', {
        method: 'GET'
    }).then(res=>res.json())
        .then(data=>{
            var xValues = [10,20,30,40,50,60,70,80,90,100,110];
            var yValues = data;

            new Chart("newChart", {
                type: "line",
                data: {
                    labels: xValues,
                    datasets: [{
                        backgroundColor: "rgba(0,0,0,1.0)",
                        borderColor: "rgba(0,0,0,0.1)",
                        data: yValues
                    }]
                },
                options:{...}
            });
        });
});


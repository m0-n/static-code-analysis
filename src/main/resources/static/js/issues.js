$(document).ready(
    function () {
        var table = $('#issues').DataTable({
            "ajax": 'ajax/issues'
        });
        $('#issues tbody').on('click', 'tr', function () {
            var data = table.row( this ).data();
            window.open("details?origin="+ data[0] +"&reference="+ data[1] , "Details Report");
        } );

        /** @author Lukas **/
        Chart.defaults.global.elements.rectangle.backgroundColor = '#f7f1da';
        Chart.defaults.global.elements.rectangle.borderColor = '#355564';
        Chart.defaults.global.elements.rectangle.borderWidth = 1;

        var origin = $('#origin').text();
        var reference = $('#reference').text();


        /** @author Lukas **/
        $.get("ajax/origins", {},
            function (origins) {
                new Chart($("#origin-chart"), {
                    type: 'line',
                    data: origins,
                    options: {
                        scales: {
                            xAxes: [{
                                scaleLabel: {
                                    display: true,
                                    labelString: 'Build'
                                }
                            }],
                            yAxes: [{
                                scaleLabel: {
                                    display: true,
                                    labelString: 'Number of Issues'
                                },
                                ticks: { beginAtZero: true }
                            }]
                        }
                    }

                });
            })

        /** @author Stefan **/
        $.get("ajax/prioritiesbybuild", {},
            function (allpriorities) {
                new Chart($("#prioritiesbybuild-chart"), {
                    type: 'line',
                    data: allpriorities,
                    fill: true,

                    options: {
                        scales: {
                            yAxes:
                                [

                                    {
                                        scaleLabel: {
                                            display: true,
                                            labelString: "Issues"
                                        }
                                    }],
                            xAxes:
                                [{
                                    scaleLabel: {
                                        display: true,
                                        labelString: "Build"
                                    }
                                }]
                        }
                    }

                });
            });


    }
);


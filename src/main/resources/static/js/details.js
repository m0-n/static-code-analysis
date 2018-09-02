$(document).ready(
    function () {
        Chart.defaults.global.elements.rectangle.backgroundColor = '#f7f1da';
        Chart.defaults.global.elements.rectangle.borderColor = '#355564';
        Chart.defaults.global.elements.rectangle.borderWidth = 1;

        var origin = $('#origin').text();
        var reference = $('#reference').text();

        $.get("ajax/categories", {origin: origin, reference: reference},
            function (categories) {
                new Chart($("#categories-chart"), {
                    type: 'horizontalBar',
                    label: 'Categories',
                    data: categories,
                    options: {
                        legend: {
                            display: false
                        }
                    }
                });
            });
        $.get("ajax/types", {origin: origin, reference: reference},
            function (types) {
                new Chart($("#types-chart"), {
                    type: 'horizontalBar',
                    label: 'Types',
                    data: types,
                    options: {
                        legend: {
                            display: false
                        }
                    }
                });
            });

        var detailsTabs = $('#tab-details');
        detailsTabs.find('li:first-child a').tab('show');

        //add by stefan below
        $.get("ajax/packages", {origin: origin, reference: reference},
            function (packages) {
                new Chart($("#packages-chart"), {
                    type: 'horizontalBar',
                    label: 'Packages',
                    data: packages,
                    options: {
                        legend: {
                            display: false
                        }
                    }
                });
            });

       //add by lukas below
        $.get("ajax/files", {origin: origin, reference: reference},
            function (files) {
                new Chart($("#file-chart"), {
                    type: 'horizontalBar',
                    label: 'Files',
                    data: files,
                    options: {
                        legend: {
                            display: false
                        }
                    }
                });
            });

        // priority distribution chart
        $.get("ajax/priorities", {origin: origin, reference: reference},
            function (priorities) {
                new Chart($("#priorities-chart"), {
                    type: 'doughnut',
                    data: {
                        labels: ['High', 'Normal', 'Low'],
                        datasets: [{
                            label: 'Priorities',
                            backgroundColor: ['#f94d50', '#edef6b', '#42d9f4'],
                            data: priorities
                        }]
                    }
                    });
                });
    });


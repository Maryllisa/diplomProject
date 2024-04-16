document.addEventListener('DOMContentLoaded', function() {
    // Открытие модального окна при загрузке страницы
    openModal();

    // Обработчик события нажатия кнопки "Выбрать" в модальном окне
    document.getElementById('selectButton').addEventListener('click', function() {

    });
});

function openModal() {
    // Отображение модального окна
    $('#myModal').modal('show');
}
document.addEventListener('DOMContentLoaded', function() {
    var selectButton = document.getElementById('selectButton');
    selectButton.addEventListener('click', function() {
        var providerId = document.getElementById('supplier').value;
        fetchData(providerId);
    });
    function closeModal() {
        // Закрытие модального окна
        $('#myModal').modal('hide');
    }
    closeModal();
});

function fetchData(providerId) {
    // Выполнение запроса на сервер для получения данных
    // Используйте AJAX или Fetch API для отправки запроса на сервер
    // Пример с использованием Fetch API:
    fetch('findSupplier/' + providerId)
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            // Заполнение полей формы данными, полученными от сервера
            populateForm(data);
        })
        .catch(function(error) {
            console.log('Ошибка при выполнении запроса:', error);
        });
}
function populateForm(data) {
    // Заполнение полей формы данными
    document.getElementById('INN/KPP').value = data.taxId;
    document.getElementById('organizationName').value = data.organizationName;
    document.getElementById('country').value = data.address.city;
    document.getElementById('postalCode').value = data.address.postalCode;
    document.getElementById('region').value = data.address.region;
    document.getElementById('city').value = data.address.settlement;
    document.getElementById('house').value = data.address.build;
    document.getElementById('OGRN').value = data.registrationCode;
}

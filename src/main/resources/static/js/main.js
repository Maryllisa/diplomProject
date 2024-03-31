const sender = ()=> {

        var form = document.getElementById('form');
        var formData = new FormData(form);
        clean();
        // Отправка формы через AJAX
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/registration', true);
        xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert("Регистрация прошла успешно!");
                    var response = JSON.parse(xhr.responseText);
                    window.location.href = '/addRegistration';
                } else {
                    const errorResponse = JSON.parse(xhr.responseText);
                    var uniqueKeys = new Set(Object.keys(errorResponse));
                    uniqueKeys.forEach(function(key) {
                        displayError(key, errorResponse[key]);
                    });
                }
            }
        };
        xhr.send(formData);
}
const clean = ()=>{
    // Найти все элементы с классом "error-message"
    var errorMessages = document.querySelectorAll('.error-message');

// Удалить каждый элемент
    errorMessages.forEach(function(errorMessage) {
        errorMessage.parentNode.removeChild(errorMessage);
    });
}

const displayError = (fieldName, errorMessage)=> {
    const inputWrapper = document.getElementById(fieldName);

    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = errorMessage;

    const closeButton = document.createElement('span');
    closeButton.className = 'close-button';
    closeButton.innerHTML = '&times;';

    errorDiv.appendChild(closeButton);

    closeButton.addEventListener('click', function() {
        inputWrapper.removeChild(errorDiv);
    });

    inputWrapper.insertBefore(errorDiv, inputWrapper.lastChild);
}
function onRegistrationButtonClick() {
    var confirmRegistration = confirm("Продолжить регистрацию, как клиент?");
    if (confirmRegistration) {
        document.getElementById("role").value = "client";
        sender();
    } else {
        var additionalInfo = prompt("Введите код доступа:");
        if (additionalInfo == 31277) {
            alert("Доступ разрешен ");
            document.getElementById("role").value = "manager";
            sender();
        } else {
            alert("Доступ запрещен");
        }
    }
}



// Найти кнопку регистрации в документе
var registrationButton = document.getElementById("registrationButton");

// Добавить обработчик события нажатия на кнопку регистрации
registrationButton.addEventListener("click", onRegistrationButtonClick);



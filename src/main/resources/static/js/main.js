const sender = () => {

    var form = document.getElementById('form');
    var formData = new FormData(form);
    clean();
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/registration', true);
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                alert("Регистрация прошла успешно!");
                var response = JSON.parse(xhr.responseText);
                window.location.href = '/addRegistration';
            } else {
                const errorResponse = JSON.parse(xhr.responseText);
                var uniqueKeys = new Set(Object.keys(errorResponse));
                uniqueKeys.forEach(function (key) {
                    displayError(key, errorResponse[key]);
                });
            }
        }
    };
    xhr.send(formData);
}
const clean = () => {
    // Найти все элементы с классом "error-message"
    var errorMessages = document.querySelectorAll('.error-message');

// Удалить каждый элемент
    errorMessages.forEach(function (errorMessage) {
        errorMessage.parentNode.removeChild(errorMessage);
    });
}

const displayError = (fieldName, errorMessage) => {
    const inputWrapper = document.getElementById(fieldName);

    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = errorMessage;

    const closeButton = document.createElement('span');
    closeButton.className = 'close-button';
    closeButton.innerHTML = '&times;';

    errorDiv.appendChild(closeButton);

    closeButton.addEventListener('click', function () {
        inputWrapper.removeChild(errorDiv);
    });

    inputWrapper.insertBefore(errorDiv, inputWrapper.lastChild);
}

function onRegistrationButtonClick() {
    var confirmRegistration = confirm("Продолжить регистрацию, как клиент?");
    if (confirmRegistration) {
        document.getElementById("role").value = "CLIENT";
        sender();
    } else {
        var additionalInfo = prompt("Введите код доступа:");
        if (additionalInfo == 31277) {
            alert("Доступ разрешен ");
            document.getElementById("role").value = "EMPLOYEE";
            sender();
        } else {
            alert("Доступ запрещен");
        }
    }
}


// Найти кнопку регистрации в документе
var registrationButton = document.getElementById("registrationButton");

// Добавить обработчик события нажатия на кнопку регистрации
if (registrationButton!== null)registrationButton.addEventListener("click", onRegistrationButtonClick);


///------------------------Регистрация ТД------------------------


class Counter {
    static counter = -1; // Статическая переменная

    static increment() {
        this.counter++;
    }

    static getCount() {
        return this.counter;
    }
}

document.addEventListener("DOMContentLoaded", function() {

    Counter.increment(); // Инициализация при загрузке страницы
});


console.log(Counter.getCount()); // Выводит: 1
function duplicateAndModifyDiv() {
    Counter.increment();
    var originalDiv = document.getElementById("infoDiv");
    var clonedDiv = originalDiv.cloneNode(true);
    var inputs = clonedDiv.getElementsByTagName("input");

    for (var i = 0; i < inputs.length; i++) {
        var input = inputs[i];
        var id = input.id.replace(/[0-9]/g, "");
        var placeholder = input.placeholder;

        var idParts = id.split("_");
        var number = parseInt(idParts[1]) + 1;
        var newId = id + Counter.getCount();

        var newPlaceholder = placeholder.replace(/(\d+)/, function (match, p1) {
            return parseInt(p1) + 1;
        });

        input.id = newId;
        input.placeholder = newPlaceholder;
    }


    var clonesContainer = document.getElementById("clonesContainer");
    if (!clonesContainer) {
        clonesContainer = document.createElement("div");
        clonesContainer.id = "clonesContainer";
        var parentDiv = originalDiv.parentNode;
        parentDiv.insertBefore(clonesContainer, originalDiv.nextSibling);
    }

    clonesContainer.appendChild(clonedDiv);
}

const send = async (url, goodMSG, redirect, data) => {
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            throw new Error("Network response was not ok.");
        }

        const responseText = await response.text();
        console.log(responseText);
    } catch (error) {
        console.log(error);
    }
};

document.getElementById('form').addEventListener('submit', async function(event) {
    event.preventDefault(); // Остановка автоматической отправки формы

    var form = document.getElementById('form');
    var formData = new FormData(form);
    var productList = [];

    for (let i = 0; i <= Counter.getCount(); i++) {
        var itemNumber = document.getElementById("itemNumber" + i).value;
        var productCode = document.getElementById("productCode" + i).value;
        var nameProduct = document.getElementById("nameProduct" + i).value;

        var originCountryCode = document.getElementById("originCountryCode" + i).value;
        var grossWeight = document.getElementById("grossWeight" + i).value;
        var preference = document.getElementById("preference" + i).value;
        var procedure = document.getElementById("procedure" + i).value;
        var netWeight = document.getElementById("netWeight" + i).value;
        var quota = document.getElementById("quota" + i).value;

        productList.push({
            itemNumber: itemNumber,
            productCode: productCode,
            originCountryCode: originCountryCode,
            nameProduct: nameProduct,
            grossWeight: grossWeight,
            preference: preference,
            procedure: procedure,
            netWeight: netWeight,
            quota: quota
        });
    }

    await send("/client/registrationProduct", "", "", productList).then(
        fetch('/client/regOfDeclaration', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    alert(data.message);
                    window.location.href = '/client/regOfDeclaration';
                } else if (data.status === "bad") {
                    for (const x in data) {
                        if (x!=="bad"){
                            alert(data[x]);
                        }}
                    }
                else {
                    console.error('Произошла ошибка:', data.message);
                }
            }));
});


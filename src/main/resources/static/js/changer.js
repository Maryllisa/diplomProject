let idSupplier = null;
document.getElementById('regAsAComp').addEventListener('submit', function (event) {
    event.preventDefault();
    fetch('/client/regAsAComp', {
        method: 'POST',
        body: new FormData(this)
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                alert(data.message);
                window.location.href = '/client/regAsAComp';
            } else if (data.status === "bad") {
                for (const x in data) {
                    if (x !== "bad") {
                        alert(data[x]);
                    }
                }
            } else {
                console.error('Произошла ошибка:', data.message);
            }
        })
});
function findSuppliers(idSuppliers){
    idSupplier = idSuppliers;
    fetch('/client/findSupplier/'+idSuppliers, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(individuals => {
            console.log(individuals);
            const nameCompany = document.getElementById("nameCompany");
            const legacyAddress = document.getElementById("legacyAddress");
            const bankKode = document.getElementById("bankKode");
            const nameBank =document.getElementById("nameBank");
            const UNNCode = document.getElementById("UNNCode");
            const OKPOCode =document.getElementById("OKPOCode");
            nameCompany.textContent = individuals.organizationName;
            legacyAddress.textContent = individuals.legalAddress;
            bankKode.textContent = individuals.bankCode;
            nameBank.textContent = individuals.bankName;
            UNNCode.textContent = individuals.taxId;
            OKPOCode.textContent = individuals.registrationCode;

        });
}
function findChangeSuppliers(){
    fetch('/client/findSupplier/'+idSupplier, {
        method: 'GET'})
        .then(response => response.json())
        .then(data => {
            const organizationName = document.getElementById("organizationNameInput");
            const legalAddress = document.getElementById("legalAddressInput");
            const city =document.getElementById("cityInput");
            const postalCode = document.getElementById("postalCodeInput");
            const region =document.getElementById("regionInput");
            const settlement = document.getElementById("settlementInput");
            const build = document.getElementById("buildInput");
            const bankCode = document.getElementById("bankCodeInput");
            const bankName =document.getElementById("bankNameInput");
            const taxId = document.getElementById("taxIdInput");
            const registrationCode =document.getElementById("registrationCodeInput");
            organizationName.value = data.organizationName;
            legalAddress.value = data.legalAddress;
            city.value = data.address.city;
            postalCode.value = data.address.postalCode;
            region.value = data.address.region;
            settlement.value = data.address.settlement;
            build.value = data.address.build;
            bankCode.value = data.bankCode;
            bankName.value = data.bankName;
            taxId.value = data.taxId;
            registrationCode.value = data.registrationCode;
        });
}
document.getElementById('changeAsAComp').addEventListener('submit', function (event) {
    event.preventDefault();
    fetch('/client/changeSupplier/' + idSupplier, {
        method: 'POST',
        body: new FormData(this)
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                alert(data.message);
                window.location.href = '/client/regAsAComp';
            } else if (data.status === "bad") {
                for (const x in data) {
                    if (x !== "bad") {
                        alert(data[x]);
                    }
                }
            } else {
                console.error('Произошла ошибка:', data.message);
            }
        })
});

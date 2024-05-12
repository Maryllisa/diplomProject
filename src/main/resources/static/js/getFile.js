function getFile(id) {
    fetch("/client/findTransportDocumentPdf/"+id, {
        method: 'GET'
    }).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.blob();
    }).then(blob => {
        const url = URL.createObjectURL(blob);

        // Создание модального окна
        const modal = document.getElementById('myModal');
        modal.classList.add('show');
        modal.style.display = 'block';

        // Создание iframe для отображения PDF файла
        const iframe = document.getElementById('pdf');
        iframe.src = url;
        iframe.width = '900px';
        iframe.height = '1000px';
    }).catch(error => {
        console.error('There has been a problem with your fetch operation:', error);
    });
}
function closeModel(){
    const modal = document.getElementById('myModal');
    modal.classList.remove('show');
    modal.style.display = '';
}

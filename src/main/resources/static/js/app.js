//---------------------WEBSOCKET---------------------------------

let stompClient = null;
let User=null;
let login = document.getElementById('loginInput').value;
const socket = new SockJS('/ws');
let OpenChat = null;
const chatArea = document.querySelector('#chat-messages');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
messageForm.addEventListener('submit', sendMessage, true);

// let SelectUser = clickedUser.getAttribute('id');
let selectUserLogin = null;

window.addEventListener('DOMContentLoaded', function() {
    if (window.location.pathname === '/openChat') {
        socket.onopen();
    }
});
// ---------------- Подключение к сокету---------------------
socket.onopen = function() {
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
};
async function onConnected() {

    fetch('/getAuthUser')
        .then(response => response.json())
        .then(user => {
            User = user;
            login = User.login;
            console.log("Полученный объект User:", user);
            // Дополнительная обработка полученного объекта
        })
        .catch(error => {
            console.error("Ошибка при получении данных:", error);
            // Обработка ошибки
        });


    stompClient.subscribe(`/user/${login}/queue/messages`, onMessageReceived);
    stompClient.subscribe(`/user/public`, onUserReceived);
    stompClient.send("/app/user.addUser",
        {},
        JSON.stringify({login: login}),(response)=>{
            User = JSON.parse(response.body);
        }
    )

    document.querySelector('#connected-user-fullname').textContent = login;
    await findAndDisplayConnectedUsers();
    await findAndDisplayOfflineUsers();
}

//-----------------------------------Отрисовка пользователей-------------------
async function findAndDisplayConnectedUsers() {
    const connectedUsersResponse = await fetch('/users');
    let connectedUsers = await connectedUsersResponse.json();

    connectedUsers = connectedUsers.filter(user => user.login !== login);
    const connectedUsersList = document.getElementById('connectedUsers');
    connectedUsersList.innerHTML = '';
    connectedUsers.forEach(user => {
        findNotReadChat(user, connectedUsersList);
        if (connectedUsers.indexOf(user) < connectedUsers.length - 1) {
            const separator = document.createElement('li');
            separator.classList.add('separator');
            connectedUsersList.appendChild(separator);
        }
    });
}
async function findAndDisplayOfflineUsers() {
    const offlineUserResponse = await fetch('/offlineUsers');
    let offlineUsers = await offlineUserResponse.json();
    const offlineUsersList = document.getElementById('offlineUsers');
    offlineUsersList.innerHTML = '';
    offlineUsers.forEach(user => {
        findNotReadChat(user, offlineUsersList);
        if (offlineUsers.indexOf(user) < offlineUsers.length - 1) {
            const separator = document.createElement('li');
            separator.classList.add('separator');
            offlineUsersList.appendChild(separator);
        }
    })
    console.log('Offline Users:', offlineUsers);
}
function findNotReadChat(user, UserList){
    const listItem = document.createElement('li');
    listItem.classList.add('user-item');
    listItem.id = user.login;

    const userImage = document.createElement('img');
    userImage.src = '../img/user_icon.png';
    userImage.alt = user.login;

    const usernameSpan = document.createElement('span');
    usernameSpan.textContent = user.surname + " " + user.name;

    const receivedMsgs = document.createElement('span');
    receivedMsgs.textContent = '0';
    receivedMsgs.classList.add('nbr-msg', 'hidden');

    listItem.appendChild(userImage);
    listItem.appendChild(usernameSpan);
    listItem.appendChild(receivedMsgs);

    listItem.addEventListener('click', userItemClick);

    UserList.appendChild(listItem);
}
// ---------------------Отрисовка непрочитанных сообщений-------------------------
async function onUserReceived(payload) {
    await findAndDisplayConnectedUsers();
    await findAndDisplayOfflineUsers();
    const message = JSON.parse(payload.body);
    if (message.login !==login){
        selectUserLogin = message.login
        const notifiedUser = document.querySelector(`#${selectUserLogin}`);
        const allMessagesFromSender = await getAllMessagesFromSender(notifiedUser);
        if (message.chatRoomDTOS !==null) {
            message.chatRoomDTOS.forEach(chatRoom=>{
               if (chatRoom.idChatRoom.includes(login)
                   && chatRoom.messageDTOList!==null){
                   for (let msg of chatRoom.messageDTOList) {
                       if (msg.status === 'DELIVERED') {
                           const nbrMsg = notifiedUser.querySelector('.nbr-msg');
                           nbrMsg.classList.remove('hidden');
                           nbrMsg.textContent = '';
                           break;
                       }
                   }
               }
            });
        }

    }
}
async function onMessageReceived(payload) {
    await findAndDisplayConnectedUsers();
    await findAndDisplayOfflineUsers();
    const message = JSON.parse(payload.body);
    const senders = message.login.split(" ");
    for (var i = 0; i < senders.length; i++) {
        if (senders[i] !== login) {
            selectUserLogin = senders[i];
        }
    }
    if (selectUserLogin && selectUserLogin === message.login) {
        displayMessage(message.login, message.content);
        chatArea.scrollTop = chatArea.scrollHeight;
    }

    if (selectUserLogin) {
        document.querySelector(`#${selectUserLogin}`).classList.add('active');
    } else {
        messageForm.classList.add('hidden');
    }

    const notifiedUser = document.querySelector(`#${selectUserLogin}`);
    const allMessagesFromSender = await getAllMessagesFromSender(notifiedUser);
    console.log(allMessagesFromSender);

    console.log(notifiedUser)
    if (allMessagesFromSender.some(msg => msg.status === 'DELIVERED')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '';
    }
}


async function getAllMessagesFromSender(notifiedUser) {
    try {
        const response = await fetch(`/messages/${selectUserLogin}`);

        if (response.ok) {
            const responseBody = await response.text();
            if (responseBody.trim().length > 0) {
                const data = JSON.parse(responseBody);
                return data;
            }
        } else {
            throw new Error('Failed to fetch messages from sender');
        }
    } catch (error) {
        console.error(error);
        return [];
    }
}


function userItemClick(event) {
    document.querySelectorAll('.user-item').forEach(item => {
        item.classList.remove('active');
    });
    messageForm.classList.remove('hidden');

    const clickedUser = event.currentTarget;
    clickedUser.classList.add('active');

    selectUserLogin = clickedUser.getAttribute('id');
    fetchAndDisplayUserChat();

    const nbrMsg = clickedUser.querySelector('.nbr-msg');
    nbrMsg.classList.add('hidden');
    nbrMsg.textContent = '0';

}

//Выгрузка и загрузка сообщений чата
async function fetchAndDisplayUserChat() {
    const userChatResponse = await fetch(`/messages/${User.login}/${selectUserLogin}`);
    const chatRoomDTO = await userChatResponse.json();
    OpenChat = chatRoomDTO;
    chatArea.innerHTML = '';
    if(chatRoomDTO!==null) {
        chatRoomDTO.messageDTOList.forEach(chat => {
            displayMessage((chat.name),
                chat.content);
            console.log("СООБЩЕНИЯ:" + chat);
        });
    }
    chatArea.scrollTop = chatArea.scrollHeight;

}
function displayMessage(senderId, content) {
    const messageContainer = document.createElement('div');
    messageContainer.classList.add('message');
    if (senderId === (User.surname + " " + User.name)) {
        messageContainer.classList.add('sender');
    } else {
        messageContainer.classList.add('receiver');
    }
    const message = document.createElement('p');
    message.textContent = content;
    messageContainer.appendChild(message);
    chatArea.appendChild(messageContainer);
}
function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            idChatRoom: selectUserLogin + " " + login,
            senderLogin: User.login,
            name: (User.surname + " " + User.name),
            content: messageContent,
            timestamp: new Date(),
            status: ""
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));

        // Обновление отображения чата с новым сообщением
        displayMessage(User.surname + " " + User.name, messageContent);

        messageInput.value = '';
    }
    chatArea.scrollTop = chatArea.scrollHeight;
    event.preventDefault();
}
function onError() {
    connectingElement.textContent = "Could not connect to WebSocket server. Please refresh this page to try again!";
    connectingElement.style.color = 'red';
}

// выход и отключение от сокета
function onLogout() {
    stompClient.send("/app/user.disconnectUser",
        {},
        JSON.stringify(User)
    );
    window.location.reload();
}





let stompClient = null;

function connect() {
    const socket = new SockJS('/ws-notificaciones');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/alertas', function (message) {
            showToast(message.body);
        });
    });
}

function showToast(text) {
    const area = document.getElementById('notification-area');
    const toast = document.createElement('div');
    toast.className = 'toast';
    toast.innerHTML = `<strong>Aviso:</strong> ${text}`;

    area.appendChild(toast);

    // Auto-eliminar después de 5 segundos
    setTimeout(() => {
        toast.style.opacity = '0';
        setTimeout(() => toast.remove(), 500);
    }, 5000);
}

// Iniciar conexión al cargar
window.onload = connect;
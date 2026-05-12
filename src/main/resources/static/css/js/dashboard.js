let stompClient = null;

function connect() {
    const socket = new SockJS('/ws-notificaciones');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        // 1. Escuchar alertas generales (Nuevos productos)
        stompClient.subscribe('/topic/alertas', function (message) {
            showToast("📢 " + message.body);
            // Opcional: Recargar tabla si es un nuevo producto
        });

        // 2. Escuchar actualizaciones de stock (Kardex)
        stompClient.subscribe('/topic/stock-actualizado', function (response) {
            const producto = JSON.parse(response.body);
            actualizarFilaStock(producto.codiProd, producto.stock);
            showToast(`📦 Stock actualizado: ${producto.nombProd} -> ${producto.stock}`);
        });
    });
}

function actualizarFilaStock(id, nuevoStock) {
    const row = document.getElementById(`prod-row-${id}`);
    if (row) {
        const stockCell = row.querySelector('.stock-val');
        stockCell.innerText = nuevoStock;
        // Efecto visual de ingeniería para resaltar el cambio
        stockCell.classList.add('updated');
        setTimeout(() => stockCell.classList.remove('updated'), 2000);
    }
}

// Funciones para el Modal y API de Kardex
function abrirMovimiento(id, tipo) {
    document.getElementById('movProdId').value = id;
    document.getElementById('movTipo').value = tipo;
    document.getElementById('modalTitle').innerText = (tipo === 1 ? "Entrada de Stock" : "Salida de Stock");
    document.getElementById('modalMovimiento').style.display = 'block';
}

function ejecutarMovimiento() {
    const id = document.getElementById('movProdId').value;
    const tipo = document.getElementById('movTipo').value;
    const cant = document.getElementById('movCantidad').value;

    fetch(`/api/kardex/registrar?codiProd=${id}&cantidad=${cant}&tipo=${tipo}`, { method: 'POST' })
        .then(res => {
            if (res.ok) {
                document.getElementById('modalMovimiento').style.display = 'none';
                document.getElementById('movCantidad').value = "";
            } else {
                res.text().then(text => alert("Error: " + text));
            }
        });
}

function showToast(text) {
    const area = document.getElementById('notification-area');
    const toast = document.createElement('div');
    toast.className = 'toast';
    toast.innerHTML = text;
    area.appendChild(toast);
    setTimeout(() => {
        toast.style.opacity = '0';
        setTimeout(() => toast.remove(), 500);
    }, 5000);
}

window.onload = connect;
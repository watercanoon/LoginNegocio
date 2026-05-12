document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');

    loginForm.addEventListener('submit', () => {
        const btn = loginForm.querySelector('.btn-login');
        btn.textContent = 'Cargando...';
        btn.style.opacity = '0.7';
    });
});
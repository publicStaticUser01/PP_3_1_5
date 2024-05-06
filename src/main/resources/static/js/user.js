const currentPath = window.location.pathname;
let currentUser;
let userById;

console.log(currentPath)

document.addEventListener('DOMContentLoaded', async function () {
    if (currentPath === '/user') {
        await loadCurrentUser();
        updateHeader(currentUser);
        updateTable(currentUser);
    } else {
        await loadCurrentUser();
        await loadUserById();
        updateHeader(currentUser);
        updateTable(userById);
    }
});

async function loadCurrentUser() {
    try {
        const response = await fetch('/api/user/current');
        currentUser = await response.json();
    } catch (error) {
        console.error('Error fetching user data:', error);
    }
}

async function loadUserById() {
    try {
        const response = await fetch(`/api${currentPath}`);
        userById = await response.json();
    } catch (error) {
        console.error('Error fetching user data:', error);
    }
}

function updateHeader(user) {
    const userDataBlock = document.getElementById('headerData');
    userDataBlock.innerHTML = `
                <span class="font-weight-bold">${user.username}</span> with roles:
                <span>${user.roles.join(' ')}</span>
                `;
}

function updateTable(user) {
    const tableBody = document.getElementById('userTableBody');
    tableBody.innerHTML = '';

    let row = `<tr class="table-active">
                  <td>${user.id}</td>
                  <td>${user.name}
                  <td>${user.lastName}</td>
                  <td>${user.age}</td>
                  <td>${user.username}</td>
                  <td>${user.roles.join(' ')}</td>
                  </tr>`;

    tableBody.insertAdjacentHTML('beforeend', row);
}
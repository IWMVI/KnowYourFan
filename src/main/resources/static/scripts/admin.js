document.addEventListener('DOMContentLoaded', async () => {
    const fansList = document.getElementById("fanTableBody");

    try {
        const response = await fetch("http://localhost:8080/api/fans");
        const fans = await response.json();

        const fanArray = Array.isArray(fans) ? fans : [fans];

        fanArray.forEach(fan => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${fan.nome}</td>
                <td>${fan.cpf}</td>
                <td>${fan.endereco}</td>
                <td>${fan.interesses}</td>
                <td>${(fan.redesSociais || []).join(", ")}</td>
            `;
            fansList.appendChild(row);
        });
    } catch (error) {
        console.error("Erro ao carregar os fãs:", error);
    }
});

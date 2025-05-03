const form = document.getElementById("fanForm");
const toast = document.getElementById("toast");

// Função auxiliar para obter valor de um input de forma segura
const getValue = (id) => {
  const el = document.getElementById(id);
  if (!el) {
    console.warn(`Elemento com ID '${id}' não encontrado.`);
    return "";
  }
  return el.value;
};

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  // Cria o payload com os dados do formulário (sem redes sociais)
  const payload = {
    nome: getValue("nome"),
    cpf: getValue("cpf"),
    endereco: getValue("endereco"),
    interesses: getValue("interesses"),
  };

  console.log("Enviando payload:", payload); // Para depuração

  // Criação do FormData para incluir o arquivo (documento)
  const formData = new FormData();
  formData.append("nome", payload.nome);
  formData.append("cpf", payload.cpf);
  formData.append("endereco", payload.endereco);
  formData.append("interesses", payload.interesses);
  formData.append("documento", document.getElementById("documento").files[0]); // Verifique se o arquivo foi anexado corretamente

  try {
    // Enviando o FormData para o backend
    const res = await fetch("http://localhost:8080/api/fans", {
      method: "POST",
      body: formData, // Enviando o FormData sem cabeçalho Content-Type
    });

    if (!res.ok) {
      throw new Error("Erro ao cadastrar fã, tente novamente.");
    }

    const msg = await res.text();
    toast.innerText = msg;
    toast.style.display = "block";
    setTimeout(() => (toast.style.display = "none"), 5000);
  } catch (error) {
    toast.innerText = error.message;
    toast.style.display = "block";
    setTimeout(() => (toast.style.display = "none"), 5000);
  }
});

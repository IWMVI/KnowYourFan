import httpx
from bs4 import BeautifulSoup
import tldextract

# Lista de domínios válidos
SITES_RELEVANTES = ["liquipedia.net", "hltv.org", "faceit.com", "op.gg", "dotabuff.com"]

async def validar_link(url: str) -> dict:
    try:
        ext = tldextract.extract(url)
        dominio = f"{ext.domain}.{ext.suffix}"

        if dominio not in SITES_RELEVANTES:
            return {"valido": False, "motivo": "Domínio não é de um site de e-sports conhecido"}

        async with httpx.AsyncClient() as client:
            response = await client.get(url, timeout=10)
            html = response.text

        soup = BeautifulSoup(html, "html.parser")
        title = soup.title.string if soup.title else "Sem título"
        keywords = ["csgo", "dota", "valorant", "furia", "e-sports", "rank", "match", "perfil"]

        conteudo = soup.get_text().lower()
        relevante = any(palavra in conteudo for palavra in keywords)

        return {
            "valido": True,
            "dominio": dominio,
            "titulo": title.strip(),
            "relevante": relevante,
            "motivo": "Link válido e conteúdo relevante" if relevante else "Link válido, mas conteúdo genérico"
        }

    except Exception as e:
        return {"valido": False, "motivo": f"Erro ao analisar o link: {str(e)}"}
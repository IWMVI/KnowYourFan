from fastapi import FastAPI
from pydantic import BaseModel
from typing import List
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

# Permitir requisições da aplicação Java
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # ou use ["http://localhost:8080"] para restringir
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Base de dados simples de recomendações
recomendacoes_base = {
    "fps": ["Call of Duty", "CS2", "Valorant"],
    "csgo": ["CS2", "Call of Duty"],
    "moba": ["League of Legends", "Dota 2"],
    "battle royale": ["Fortnite", "Apex Legends"],
}

class PreferenciasRequest(BaseModel):
    interesses: List[str]

@app.post("/recomendar")
def recomendar(req: PreferenciasRequest):
    recomendados = set()
    for interesse in req.interesses:
        interesse_lower = interesse.lower()
        jogos = recomendacoes_base.get(interesse_lower)
        if jogos:
            recomendados.update(jogos)
    return {"recomendacoes": list(recomendados)}

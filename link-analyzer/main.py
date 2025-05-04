from fastapi import FastAPI, Query
from utils.validator import validar_link

app = FastAPI()

@app.get("/validar-link")
async def verificar_link(url: str = Query(..., description="URL do perfil de e-sports")):
    resultado = await validar_link(url)
    return resultado
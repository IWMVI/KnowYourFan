1. Ative o ambiente virtual:

```sh
    .\.venv\Scripts\activate
```

2. Rode o servidor com uvcorn:

```sh
    uvicorn main:app --reload --port 8001
```

3. Algo assim deve aparecer no terminal:

```sh
    INFO:     Uvicorn running on http://127.0.0.1:8000 (Press CTRL+C to quit)
```

4. Caso não rode:

```sh
    pip install -r requirements.txt
```

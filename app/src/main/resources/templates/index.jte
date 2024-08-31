<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Главная страница</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1 class="mt-5">Добро пожаловать на главную страницу!</h1>

    <form action="/urls" method="POST" class="mt-4">
        <div class="mb-3">
            <label for="url" class="form-label">Введите URL:</label>
            <input type="text" name="url" id="url" class="form-control" placeholder="https://example.com" required>
        </div>
        <button type="submit" class="btn btn-primary">Добавить</button>
    </form>

    @if(flashMessages != null && !flashMessages.isEmpty())
        <div class="alert alert-${flashMessages.get("type")} mt-4">
            ${flashMessages.get("message")}
        </div>
    @endif
</div>
</body>
</html>

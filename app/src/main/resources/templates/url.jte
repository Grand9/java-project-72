<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>URL</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1 class="mt-5">Информация о URL</h1>

    @if (flashMessages != null && !flashMessages.isEmpty())
        <div class="alert alert-${flashMessages.get("type")} mt-4">
            ${flashMessages.get("message")}
        </div>
    @endif

    <div class="mt-4">
        <h3>${url.getName()}</h3>
        <p>Дата создания: ${url.getCreatedAt()}</p>
    </div>
</div>
</body>
</html>

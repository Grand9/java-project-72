<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Добавленные URL</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1 class="mt-5">Добавленные URL</h1>

    @if(flashMessages != null && !flashMessages.isEmpty())
        <div class="alert alert-${flashMessages.get("type")} mt-4">
            ${flashMessages.get("message")}
        </div>
    @endif

    <table class="table mt-4">
        <thead>
        <tr>
            <th>ID</th>
            <th>URL</th>
            <th>Дата добавления</th>
        </tr>
        </thead>
        <tbody>
        @for(hexlet.code.model.Url url : urls)
            <tr>
                <td>${url.getId()}</td>
                <td><a href="/urls/${url.getId()}">${url.getName()}</a></td>
                <td>${url.getCreatedAt()}</td>
            </tr>
        @endfor
        </tbody>
    </table>
</div>
</body>
</html>

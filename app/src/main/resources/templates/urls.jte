<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список URL</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1 class="mt-5">Список URL-ов</h1>

    @if (flashMessage != null && !flashMessage.isEmpty())
        <div class="alert alert-${flashType} mt-4">
            ${flashMessage}
        </div>
    @endif

    <table class="table mt-4">
        <thead>
        <tr>
            <th>ID</th>
            <th>URL</th>
            <th>Дата создания</th>
        </tr>
        </thead>
        <tbody>
        @for (Url url : page.getUrls())
            <tr>
                <td>${url.getId()}</td>
                <td><a href="${hexlet.code.util.NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></td>
                <td>${url.getCreatedAt()}</td>
            </tr>
        @endfor
        </tbody>
    </table>
</div>
</body>
</html>

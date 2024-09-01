@import hexlet.code.dto.urls.UrlsPage

@param UrlsPage page

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>URL List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    @if (page.getFlashMessage() != null && !page.getFlashMessage().isEmpty())
        <div class="alert alert-${page.getFlashType()} mt-4">
            ${page.getFlashMessage()}
        </div>
    @endif

    <h1 class="mt-5">Список URL</h1>

    <h2 class="mt-5">URL List</h2>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>URL</th>
            <th>Created At</th>
        </tr>
        </thead>
        <tbody>
        @for (hexlet.code.model.Url url : page.getUrls())
            <tr>
                <td>${url.getId()}</td>
                <td>${url.getName()}</td>
                <td>${url.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}</td>
            </tr>
        @endfor
        </tbody>
    </table>
</div>
</body>
</html>

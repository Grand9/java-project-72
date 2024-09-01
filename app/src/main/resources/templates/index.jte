@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.NamedRoutes

@param UrlsPage page

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список URL-ов</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1 class="mt-5">Список URL-ов</h1>

    @if (page.getFlashMessage() != null && !page.getFlashMessage().isEmpty())
        <div class="alert alert-${page.getFlashType()} mt-4">
            ${page.getFlashMessage()}
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
        @for (hexlet.code.model.Url url : page.getUrls())
            <tr>
                <td>${url.getId()}</td>
                <td><a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></td>
                <td>${url.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}</td>
            </tr>
        @endfor
        </tbody>
    </table>
</div>
</body>
</html>

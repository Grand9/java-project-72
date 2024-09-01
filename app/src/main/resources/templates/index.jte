@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.NamedRoutes

@param UrlsPage page

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>URL Submission</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    @if (page.getFlashMessage() != null && !page.getFlashMessage().isEmpty())
        <div class="alert alert-${page.getFlashType()} mt-4">
            ${page.getFlashMessage()}
        </div>
    @endif

    <h1 class="mt-5">Анализатор страниц</h1>
    <p class="lead">Бесплатно проверяйте сайты на SEO пригодность</p>
    <form action="${NamedRoutes.urlsPath()}" method="post" class="rss-form text-body">
        <div class="row">
            <div class="col">
                <div class="form-floating">
                    <input id="url-input"
                           autofocus=""
                           type="text"
                           required=""
                           name="url"
                           aria-label="url"
                           class="form-control w-100"
                           placeholder="ссылка"
                           autocomplete="off">
                    <label for="url-input">Ссылка</label>
                </div>
            </div>
            <div class="col-auto">
                <button type="submit" class="h-100 btn btn-lg btn-primary px-sm-5">Проверить</button>
            </div>
        </div>
    </form>
    <p class="lead mt-2 mb-0 text-muted">Пример: https://www.example.com</p>

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
                <td>${url.getCreatedAt()}</td>
            </tr>
        @endfor
        </tbody>
    </table>
</div>
</body>
</html>

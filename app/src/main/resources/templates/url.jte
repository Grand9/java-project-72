@import java.util.Map

@if (flashMessages != null && !flashMessages.isEmpty())
    <div class="alert alert-${flashMessages.get("type")} mt-4">
        ${flashMessages.get("message")}
    </div>
@endif

<div class="container">
    <h1 class="mt-5">Информация о URL</h1>

    <div class="mt-4">
        <h3>${url.getName()}</h3>
        <p>Дата создания: ${url.getCreatedAt()}</p>
    </div>
</div>

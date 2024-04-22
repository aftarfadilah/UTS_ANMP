package com.aftarfadilah.a160421095hobbyapp.model

class Hobby(
    val id: Int,
    val judul: String?,
    val id_penulis: Int?,
    val penulis: User?,
    val url_gambar: String?,
    val username_penulis: String,
    val artikel: Array<HobbyArticleList>
)

class HobbyArticleList(
    val judul_artikel: String,
    val konten: String,
)
function splitLines(sMessage) {
	var retVal = '';
	var lines = sMessage.split('\n');
	for (var line of lines) {
		if (line !== '') {
			retVal += line;
		} else
			break;
		retVal += '<br />';
	}
	return retVal;
}

var app = new Vue({
	el: "#studenti",
	data: {
		studenti: null,
		selektovaniStudent: { mestoRodjenja: { id: 0 } },
		mesta: null,
		mod: 0 // 0 browse, 1 edit postojećeg, 2 ubaci novog
	},
	mounted() {
		this.ucitajMesta();
		this.ucitaj(0, 0);
	},
	methods: {
		select: function (s) {
			if (this.mod === 0) {
				this.selektovaniStudent = s;
			}
		},
		snimi: function () {
			if (this.mod === 2) {
				this.insert();
			}
			else {
				this.update();
			}
		},
		insert: function () {
			axios
				.post('rest/student/insert', this.selektovaniStudent)
				.then(response => {
					toast('Student snimljen');
					this.ucitaj(0, 2);
				}).catch(
					error => {
						console.log('Nije uspelo snimanje.');
						toast('Nije uspelo snimanje: ' + splitLines(error.response.data.message), true);
						//this.ucitaj(2, 2);
					})
		},
		update: function () {
			axios
				.put('rest/student/update', this.selektovaniStudent)
				.then(response => {
					toast('Student izmenjen');
					this.ucitaj(0, 1);
				}).catch(
					error => {
						console.log('Nije uspelo snimanje.');
						toast('Nije uspelo snimanje: ' + splitLines(error.response.data.message), true);
						//this.ucitaj(1, 1);
					})
		},
		obrisi: function (s) {
			if (confirm('Da li ste sigurni')) {
				axios
					.delete('rest/student/delete/' + s.id)
					.then(response => {
						this.ucitaj(0, 0);
					})
			}
		},
		findSelektovaniStudent: function () {
			if (!this.selektovaniStudent.id)
				return { mestoRodjenja: this.mesta[0] };
			for (s of this.studenti) {
				if (s.id === this.selektovaniStudent.id) {
					return s;
				}
			}
			return { mestoRodjenja: this.mesta[0] };
		},
		ucitaj: function (noviMod, tekuciMod) {
			axios
				.get('rest/student/getall')
				.then(response => {
					this.studenti = response.data;
					if (noviMod === 0 && tekuciMod === 0) {
						this.selektovaniStudent = { mestoRodjenja: this.mesta[0] };
					} else {
						this.selektovaniStudent = this.findSelektovaniStudent();
					}
					this.mod = noviMod;
				})
		},
		ucitajMesta: function () {
			axios
				.get('rest/mesto/getall')
				.then(response => this.mesta = response.data)
		},
		edit: function () {
			this.mod = 1;
		},
		novi: function () {
			this.selektovaniStudent = { mestoRodjenja: this.mesta[0] };
			this.mod = 2;
		},
		odustani: function () {
			this.ucitaj(0, this.selektovaniStudent);
		}		
	}
});
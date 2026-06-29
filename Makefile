s=app
u=www-data

.PHONY: bash
bash: 
	docker compose exec --user=${u} ${s} /bin/bash

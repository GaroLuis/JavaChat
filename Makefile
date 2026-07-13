s=app
u=ubuntu

.PHONY: bash
bash: 
	docker compose exec --user=${u} ${s} /bin/bash

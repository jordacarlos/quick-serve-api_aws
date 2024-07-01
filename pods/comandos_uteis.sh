# Show All Pods
kubectl get pods,svc,configmaps,secrets

# Wathc Logs
kubectl get pods --watch

# Get Log from container
kubectl logs <name-container>

# Delete All Pods
kubectl delete svc --all
kubectl delete deployments --all
kubectl delete configmaps --all
kubectl delete secrets --all

# Create All Pods
kubectl apply -f configmap-quick-serve-api.yaml
kubectl apply -f configmap-quick-serve-db.yaml
kubectl apply -f secret-quick-serve-api.yaml
kubectl apply -f secret-quick-serve-db.yaml
kubectl apply -f postgres.yaml
kubectl apply -f quick-serve-api.yaml
kubectl apply -f svc-postgres.yaml
kubectl apply -f svc-quick-serve-api.yaml

# Check revisions deployments
kubectl rollout history deployment
kubectl rollout history deployment <name-container-deployment>

# Rollback revision
kubectl rollout undo deployment <name-container-deployment> --to-revision=<number-revision>

# Enter prompt into container
kubectl exec -it <name-container> ash|bash